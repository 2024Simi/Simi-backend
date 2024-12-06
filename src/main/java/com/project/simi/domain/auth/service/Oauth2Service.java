package com.project.simi.domain.auth.service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.simi.domain.auth.dto.LoginDto;
import com.project.simi.domain.auth.dto.LoginDto.LoginResponse;
import com.project.simi.domain.auth.dto.LoginDto.Response;
import com.project.simi.domain.auth.dto.OIDCUserInfo;
import com.project.simi.domain.auth.enums.AuthProviderEnum;
import com.project.simi.domain.auth.enums.AuthoriryEnum;
import com.project.simi.domain.auth.enums.LoginFlagEnum;
import com.project.simi.domain.auth.provider.JwtTokenProvider;
import com.project.simi.domain.user.domain.User;
import com.project.simi.domain.user.repository.command.UserCommandRepository;
import com.project.simi.domain.user.repository.query.UserConsentQueryRepository;
import com.project.simi.domain.user.repository.query.UserQueryRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class Oauth2Service {

    private final KakaoOauth2Service kakaoOauth2Service;
    private final AppleOauth2Service appleOauth2Service;
    private final UserQueryRepository userQueryRepository;
    private final UserCommandRepository userCommandRepository;
    private final UserConsentQueryRepository userConsentQueryRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    public OIDCUserInfo getUserInfoAndVerify(AuthProviderEnum provider, String idToken) {
        try {
            return switch (provider) {
                case KAKAO -> kakaoOauth2Service.getUserInfoAndVerify(idToken);
                case APPLE -> appleOauth2Service.getUserInfoAndVerify(idToken);
                default -> throw new IllegalArgumentException("Invalid provider");
            };
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public LoginResponse loginSignUp(AuthProviderEnum provider, String idToken) {
        OIDCUserInfo oidcUserInfo = getUserInfoAndVerify(provider, idToken);
        User user = createAndGetDefaultUser(oidcUserInfo, provider);

        Response tokenResponse = generateToken(user);
        return new LoginResponse(tokenResponse, calculateLoginFlag(user));
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

    private User createAndGetDefaultUser(OIDCUserInfo oidcUserInfo, AuthProviderEnum provider) {
        Optional<User> userOptional = userQueryRepository.findByLoginId(oidcUserInfo.getEmail());
        User user =
                User.createOf(
                        oidcUserInfo.getEmail(),
                        oidcUserInfo.getSub(),
                        oidcUserInfo.getPicture(),
                        oidcUserInfo.getNickname(),
                        null,
                        List.of(AuthoriryEnum.ROLE_DEFAULT),
                        provider);
        return userOptional.orElseGet(() -> userCommandRepository.save(user));
    }

    private Response generateToken(User user) {
        String accessTokenValue = jwtTokenProvider.generateAccessTokenValue(user);
        String refreshTokenValue =
                refreshTokenService.createAndSaveRefreshToken(user).getRefreshTokenValue();
        return new LoginDto.Response(accessTokenValue, refreshTokenValue, user.getId());
    }
}
