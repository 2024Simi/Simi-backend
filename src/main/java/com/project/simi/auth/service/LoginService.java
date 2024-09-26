package com.project.simi.auth.service;

import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.simi.auth.dto.LoginDto;
import com.project.simi.auth.provider.JwtTokenProvider;
import com.project.simi.common.exception.BusinessException;
import com.project.simi.common.exception.code.ExceptionCode;
import com.project.simi.user.domain.User;
import com.project.simi.user.repository.query.UserQueryRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserQueryRepository userQueryRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    public LoginDto.Response login(LoginDto.Request request) {
        User user =
                userQueryRepository
                        .findByLoginId(request.loginId())
                        .orElseThrow(() -> new BusinessException(ExceptionCode.LOGIN_FAILED));

        if (!verifyUserCredential(request.password(), user.getPassword())) {

            throw new BusinessException(ExceptionCode.LOGIN_FAILED);
        }

        String accessTokenValue = jwtTokenProvider.generateAccessTokenValue(user);
        // 중복 로그인을 막아야 한다면, 기존 refreshToken 을 만료 시킬 것
        String refreshTokenValue =
                refreshTokenService.createAndSaveRefreshToken(user).getRefreshTokenValue();

        return new LoginDto.Response(accessTokenValue, refreshTokenValue, user.getId());
    }

    private boolean verifyUserCredential(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void logout(Long userId, String refreshTokenValue) {
        refreshTokenService.delete(userId, refreshTokenValue);
    }
}
