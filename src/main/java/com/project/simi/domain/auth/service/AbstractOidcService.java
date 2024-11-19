package com.project.simi.domain.auth.service;

import java.security.interfaces.RSAPublicKey;

import lombok.experimental.ExtensionMethod;

import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jwt.SignedJWT;
import com.project.simi.common.utils.ObjectMapperUtils;
import com.project.simi.domain.auth.cache.JWKSetCache;

@ExtensionMethod({JWKSetCache.class, ObjectMapperUtils.class})
public abstract class AbstractOidcService<T> {

    protected abstract String getJwksUrl();

    protected abstract Class<T> getUserInfoClass();

    public AbstractOidcService() {
        JWKSetCache.startJWKSetCacheRefresher(getJwksUrl(), 3600);
    }

    public T getUserInfoAndVerify(String idToken) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(idToken);
        JWKSet jwkSet = getCachedJWKSet();

        JWK jwk = jwkSet.getKeyByKeyId(signedJWT.getHeader().getKeyID());

        if (jwk == null) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

        RSASSAVerifier verifier = new RSASSAVerifier((RSAPublicKey) jwk.toRSAKey().toPublicKey());
        if (!signedJWT.verify(verifier)) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

        return signedJWT.getJWTClaimsSet().getClaims().convertValue(getUserInfoClass());
    }

    private JWKSet getCachedJWKSet() throws Exception {
        return getJwksUrl().getCachedJWKSet();
    }
}
