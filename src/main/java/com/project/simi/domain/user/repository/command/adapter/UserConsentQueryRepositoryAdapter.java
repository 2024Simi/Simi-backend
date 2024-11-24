package com.project.simi.domain.user.repository.command.adapter;

import java.util.Collection;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.project.simi.domain.user.domain.UserConsent;
import com.project.simi.domain.user.repository.UserConsentJpaRepository;
import com.project.simi.domain.user.repository.query.UserConsentQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@RequiredArgsConstructor
public class UserConsentQueryRepositoryAdapter implements UserConsentQueryRepository {
    private final JPAQueryFactory queryFactory;
    private final UserConsentJpaRepository userConsentJpaRepository;

    @Override
    public Optional<UserConsent> findByUserId(Long userId) {
        return userConsentJpaRepository.findById(userId);
    }

    @Override
    public Collection<UserConsent> findAll() {
        return userConsentJpaRepository.findAll();
    }
}
