package com.project.simi.domain.oauth.service;

import java.security.interfaces.RSAPublicKey;

import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jwt.SignedJWT;
import com.project.simi.common.utils.ObjectMapperUtils;
import com.project.simi.domain.oauth.cache.JWKSetCache;
import com.project.simi.domain.oauth.dto.KakaoUserOIDC;

@ExtensionMethod({JWKSetCache.class, ObjectMapperUtils.class})
@Service
@RequiredArgsConstructor
public class KakaoOauth2Service {
    private static final String KAKAO_JWKS_URL = "https://kauth.kakao.com/.well-known/jwks.json";

    static {
        JWKSetCache.startJWKSetCacheRefresher(KAKAO_JWKS_URL, 3600);
    }

    public KakaoUserOIDC getUserInfoAndVerify(String idToken) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(idToken);
        JWKSet jwkSet = KAKAO_JWKS_URL.getCachedJWKSet();

        JWK jwk = jwkSet.getKeyByKeyId(signedJWT.getHeader().getKeyID());

        if (jwk == null) {
            throw new IllegalArgumentException("Invalid JWT token");
        }
        RSASSAVerifier verifier = new RSASSAVerifier((RSAPublicKey) jwk.toRSAKey().toPublicKey());
        boolean isValid = signedJWT.verify(verifier);
        if (!isValid) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

        return signedJWT.getJWTClaimsSet().getClaims().convertValue(KakaoUserOIDC.class);
    }
}
