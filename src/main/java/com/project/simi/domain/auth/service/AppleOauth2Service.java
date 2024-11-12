package com.project.simi.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;

import org.springframework.stereotype.Service;

import com.project.simi.common.utils.ObjectMapperUtils;
import com.project.simi.domain.auth.cache.JWKSetCache;
import com.project.simi.domain.auth.dto.AppleUserOIDC;

@ExtensionMethod({JWKSetCache.class, ObjectMapperUtils.class})
@Service
@RequiredArgsConstructor
public class AppleOauth2Service extends AbstractOidcService<AppleUserOIDC> {

    private static final String APPLE_JWKS_URL = "https://appleid.apple.com/auth/keys";

    @Override
    protected String getJwksUrl() {
        return APPLE_JWKS_URL;
    }

    @Override
    protected Class<AppleUserOIDC> getUserInfoClass() {
        return AppleUserOIDC.class;
    }
}
