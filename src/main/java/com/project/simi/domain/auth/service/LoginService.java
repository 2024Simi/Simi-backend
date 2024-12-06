package com.project.simi.domain.auth.service;

import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.simi.common.exception.BusinessException;
import com.project.simi.common.exception.code.ExceptionCode;
import com.project.simi.domain.auth.domain.RefreshToken;
import com.project.simi.domain.auth.dto.LoginDto.LoginResponse;
import com.project.simi.domain.auth.dto.LoginDto.RefreshRequest;
import com.project.simi.domain.auth.dto.LoginDto.Request;
import com.project.simi.domain.auth.dto.LoginDto.Response;
import com.project.simi.domain.auth.enums.LoginFlagEnum;
import com.project.simi.domain.auth.provider.JwtTokenProvider;
import com.project.simi.domain.auth.repository.RefreshTokenJpaRepository;
import com.project.simi.domain.user.domain.User;
import com.project.simi.domain.user.repository.query.UserConsentQueryRepository;
import com.project.simi.domain.user.repository.query.UserQueryRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserQueryRepository userQueryRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserConsentQueryRepository userConsentQueryRepository;
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    public LoginResponse login(Request request) {
        User user =
                userQueryRepository
                        .findByLoginId(request.loginId())
                        .orElseThrow(() -> new BusinessException(ExceptionCode.LOGIN_FAILED));

        if (!verifyUserCredential(request.password(), user.getPassword())) {

            throw new BusinessException(ExceptionCode.LOGIN_FAILED);
        }

        LoginFlagEnum loginFlag = calculateLoginFlag(user);

        String accessTokenValue = jwtTokenProvider.generateAccessTokenValue(user);
        // 중복 로그인을 막아야 한다면, 기존 refreshToken 을 만료 시킬 것
        String refreshTokenValue =
                refreshTokenService.createAndSaveRefreshToken(user).getRefreshTokenValue();

        return new LoginResponse(accessTokenValue, refreshTokenValue, user.getId(), loginFlag);
    }

    private LoginFlagEnum calculateLoginFlag(User user) {
        if (!user.isNonLocked() || !user.isNotExpired()) {
            return LoginFlagEnum.WITHDRAWAL;
        }

        boolean hasUserConsent = userConsentQueryRepository.findByUserId(user.getId()).isPresent();
        boolean hasUserNickname = !user.getNickname().isEmpty();

        if (hasUserConsent && !hasUserNickname) {
            return LoginFlagEnum.NICKNAME_PENDING;
        }
        if (hasUserNickname) {
            return LoginFlagEnum.LOGIN;
        }
        return LoginFlagEnum.SIGN_IN;
    }

    private boolean verifyUserCredential(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void logout(Long userId, String refreshTokenValue) {
        refreshTokenService.delete(userId, refreshTokenValue);
    }

    public Response refresh(RefreshRequest refreshRequest) {
        RefreshToken verifiedToken =
                refreshTokenService.getVerifiedRefreshToken(refreshRequest.refreshToken());
        User user = verifiedToken.getUser();

        refreshTokenService.delete(user.getId(), verifiedToken.getRefreshTokenValue());

        String accessTokenValue = jwtTokenProvider.generateAccessTokenValue(user);
        String newRefreshTokenValue =
                refreshTokenService.createAndSaveRefreshToken(user).getRefreshTokenValue();

        return new Response(accessTokenValue, newRefreshTokenValue, user.getId());
    }
}
