package com.project.simi.domain.auth.service;

import java.security.SecureRandom;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import lombok.RequiredArgsConstructor;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.simi.common.exception.BusinessException;
import com.project.simi.common.exception.code.ExceptionCode;
import com.project.simi.domain.auth.domain.RefreshToken;
import com.project.simi.domain.auth.repository.command.RefreshTokenCommandRepository;
import com.project.simi.domain.auth.repository.query.RefreshTokenQueryRepository;
import com.project.simi.domain.user.domain.User;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${security.refresh-token.length-byte}")
    private int tokenByteLength;

    @Value("${security.refresh-token.expiry-value}")
    private long tokenExpiryValue;

    @Value("${security.refresh-token.expiry-unit}")
    private String rawTokenExpiryUnit;

    private ChronoUnit tokenExpiryUnit;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private final RefreshTokenQueryRepository refreshTokenQueryRepository;
    private final RefreshTokenCommandRepository refreshTokenCommandRepository;

    @PostConstruct
    public void init() {
        try {
            tokenExpiryUnit = ChronoUnit.valueOf(rawTokenExpiryUnit.toUpperCase());
        } catch (IllegalArgumentException e) {
            tokenExpiryUnit = ChronoUnit.DAYS;
        }
    }

    @Transactional
    public RefreshToken createAndSaveRefreshToken(User user) {
        return refreshTokenCommandRepository.save(
                RefreshToken.createOf(
                        user,
                        generateUniqueRefreshTokenValue(),
                        tokenExpiryValue,
                        tokenExpiryUnit));
    }

    private String generateUniqueRefreshTokenValue() {
        String tokenValue;
        do {
            tokenValue = generateNewRefreshTokenValue();
        } while (refreshTokenQueryRepository.existsByRefreshTokenValue(tokenValue));

        return tokenValue;
    }

    private String generateNewRefreshTokenValue() {
        byte[] randomBytes = new byte[tokenByteLength];
        secureRandom.nextBytes(randomBytes);

        return base64Encoder.encodeToString(randomBytes);
    }

    @Transactional
    public void delete(Long userId, String refreshTokenValue) {
        refreshTokenQueryRepository
                .findByUserIdAndRefreshTokenValue(userId, refreshTokenValue)
                .ifPresent(RefreshToken::expireRefreshToken);
    }

    public RefreshToken getVerifiedRefreshToken(String refreshToken) {
        return refreshTokenQueryRepository
                .findByRefreshTokenValue(refreshToken)
                .orElseThrow(() -> new BusinessException(ExceptionCode.INVALID_REFRESH_TOKEN));
    }
}
