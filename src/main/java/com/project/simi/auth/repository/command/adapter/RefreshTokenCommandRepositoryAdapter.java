package com.project.simi.auth.repository.command.adapter;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.project.simi.auth.domain.RefreshToken;
import com.project.simi.auth.repository.RefreshTokenJpaRepository;
import com.project.simi.auth.repository.command.RefreshTokenCommandRepository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenCommandRepositoryAdapter implements RefreshTokenCommandRepository {
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenJpaRepository.save(refreshToken);
    }
}
