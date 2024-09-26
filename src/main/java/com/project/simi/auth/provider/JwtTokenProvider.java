package com.project.simi.auth.provider;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.project.simi.common.exception.UnauthorizedException;
import com.project.simi.user.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Slf4j
@Component
@NoArgsConstructor
public class JwtTokenProvider {
    @Value("${security.access-token.expiry-in-milli}")
    private long tokenExpiryInMilli;

    @Value("${security.access-token.jwt-secret-key}")
    private String signatureSecretKey;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(signatureSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessTokenValue(User user) {
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpiryInMilli))
                .signWith(key)
                .compact();
    }

    public void validateToken(String jwtToken) {
        try {
            Date expiration = parseClaimsJws(jwtToken).getPayload().getExpiration();

            if (expiration.before(new Date())) {
                throw new UnauthorizedException();
            }
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            throw new UnauthorizedException();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            throw new UnauthorizedException();
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            throw new UnauthorizedException();
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
            throw new UnauthorizedException();
        }
    }

    private Jws<Claims> parseClaimsJws(String jwtToken) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken);
    }

    public Optional<Object> getClaimValue(String jwtToken, String claimName) {
        return Optional.ofNullable(parseClaimsJws(jwtToken).getPayload().get(claimName));
    }

    public String getSubject(String jwtToken) {
        Jws<Claims> jws = parseClaimsJws(jwtToken);

        return jws.getPayload().getSubject();
    }
}
