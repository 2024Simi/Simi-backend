package com.project.simi.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;

import org.springframework.stereotype.Service;

import com.project.simi.common.utils.ObjectMapperUtils;
import com.project.simi.domain.auth.cache.JWKSetCache;
import com.project.simi.domain.auth.dto.KakaoUserOIDC;

@ExtensionMethod({JWKSetCache.class, ObjectMapperUtils.class})
@Service
@RequiredArgsConstructor
public class KakaoOauth2Service extends AbstractOidcService<KakaoUserOIDC> {
    private static final String KAKAO_JWKS_URL = "https://kauth.kakao.com/.well-known/jwks.json";

    static {
        JWKSetCache.startJWKSetCacheRefresher(KAKAO_JWKS_URL, 3600);
    }

    @Override
    protected String getJwksUrl() {
        return KAKAO_JWKS_URL;
    }

    @Override
    protected Class<KakaoUserOIDC> getUserInfoClass() {
        return KakaoUserOIDC.class;
    }
}
