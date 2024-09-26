package com.project.simi.domain.auth.repository.query.adapter;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.project.simi.domain.auth.domain.RefreshToken;
import com.project.simi.domain.auth.repository.RefreshTokenJpaRepository;
import com.project.simi.domain.auth.repository.query.RefreshTokenQueryRepository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenQueryRepositoryAdapter implements RefreshTokenQueryRepository {
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public Boolean existsByRefreshTokenValue(String refreshTokenValue) {
        return refreshTokenJpaRepository.existsByRefreshTokenValue(refreshTokenValue);
    }

    @Override
    public Optional<RefreshToken> findByUserIdAndRefreshTokenValue(
            Long userId, String refreshTokenValue) {
        return refreshTokenJpaRepository.findByUser_IdAndRefreshTokenValue(
                userId, refreshTokenValue);
    }
}
