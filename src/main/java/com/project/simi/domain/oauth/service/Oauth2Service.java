package com.project.simi.domain.oauth.service;

import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.simi.domain.auth.enums.AuthProviderEnum;
import com.project.simi.domain.auth.service.LoginService;

@Service
@RequiredArgsConstructor
@Transactional
public class Oauth2Service {
    private final KakaoOauth2Service kakaoOauth2Service;
    private final LoginService loginService;

    public Object getUserInfoAndVerify(AuthProviderEnum provider, String idToken) {
        try {
            switch (provider) {
                case KAKAO:
                    return kakaoOauth2Service.getUserInfoAndVerify(idToken);
                default:
                    throw new IllegalArgumentException("Invalid provider");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
