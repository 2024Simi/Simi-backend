package com.project.simi.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.simi.auth.domain.RefreshToken;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {
    Boolean existsByRefreshTokenValue(String refreshTokenValue);

    Optional<RefreshToken> findByUser_IdAndRefreshTokenValue(Long userId, String refreshTokenValue);
}
