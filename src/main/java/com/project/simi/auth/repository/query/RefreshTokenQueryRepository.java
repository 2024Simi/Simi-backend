package com.project.simi.auth.repository.query;

import java.util.Optional;

import com.project.simi.auth.domain.RefreshToken;

public interface RefreshTokenQueryRepository {
    Boolean existsByRefreshTokenValue(String refreshTokenValue);

    Optional<RefreshToken> findByUserIdAndRefreshTokenValue(Long userId, String refreshTokenValue);
}