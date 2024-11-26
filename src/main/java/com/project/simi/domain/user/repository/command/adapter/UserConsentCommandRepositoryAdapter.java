package com.project.simi.domain.user.repository.command.adapter;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.project.simi.domain.user.domain.UserConsent;
import com.project.simi.domain.user.repository.UserConsentJpaRepository;
import com.project.simi.domain.user.repository.command.UserConsentCommandRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@RequiredArgsConstructor
public class UserConsentCommandRepositoryAdapter implements UserConsentCommandRepository {
    private final JPAQueryFactory queryFactory;
    private final UserConsentJpaRepository userConsentJpaRepository;

    public UserConsent save(UserConsent userConsent) {
        return userConsentJpaRepository.save(userConsent);
    }
}
