package com.project.simi.domain.auth.repository.command.adapter;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.project.simi.domain.auth.domain.RefreshToken;
import com.project.simi.domain.auth.repository.RefreshTokenJpaRepository;
import com.project.simi.domain.auth.repository.command.RefreshTokenCommandRepository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenCommandRepositoryAdapter implements RefreshTokenCommandRepository {
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenJpaRepository.save(refreshToken);
    }
}
