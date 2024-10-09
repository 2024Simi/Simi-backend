package com.project.simi.domain.auth.repository.query;

import java.util.Optional;

import com.project.simi.domain.auth.domain.RefreshToken;

public interface RefreshTokenQueryRepository {
    Boolean existsByRefreshTokenValue(String refreshTokenValue);

    Optional<RefreshToken> findByUserIdAndRefreshTokenValue(Long userId, String refreshTokenValue);

    Optional<RefreshToken> findByRefreshTokenValue(String refreshToken);
}
