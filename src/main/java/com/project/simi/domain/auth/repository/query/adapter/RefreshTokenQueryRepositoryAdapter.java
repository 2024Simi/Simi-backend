package com.project.simi.domain.auth.repository.query.adapter;

import static com.project.simi.domain.auth.domain.QRefreshToken.refreshToken;

import java.time.Instant;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.project.simi.domain.auth.domain.RefreshToken;
import com.project.simi.domain.auth.repository.RefreshTokenJpaRepository;
import com.project.simi.domain.auth.repository.query.RefreshTokenQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@RequiredArgsConstructor
public class RefreshTokenQueryRepositoryAdapter implements RefreshTokenQueryRepository {
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

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

    @Override
    public Optional<RefreshToken> findByRefreshTokenValue(String refreshTokenValue) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(refreshToken)
                        .where(
                                refreshToken.refreshTokenValue.eq(refreshTokenValue),
                                refreshToken.expiredDatetime.after(Instant.now()))
                        .fetchFirst());
    }
}
