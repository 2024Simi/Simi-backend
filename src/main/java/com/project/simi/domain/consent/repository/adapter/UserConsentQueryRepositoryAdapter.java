package com.project.simi.domain.consent.repository.adapter;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.project.simi.domain.consent.domain.QUserConsent;
import com.project.simi.domain.consent.domain.UserConsent;
import com.project.simi.domain.consent.repository.UserConsentQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@RequiredArgsConstructor
public class UserConsentQueryRepositoryAdapter implements UserConsentQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UserConsent> findAllByUserId(Long userId) {
        return jpaQueryFactory
                .selectFrom(QUserConsent.userConsent)
                .where(QUserConsent.userConsent.createdBy.eq(userId))
                .fetch();
    }
}
