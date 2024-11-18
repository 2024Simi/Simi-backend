package com.project.simi.domain.consent.repository.adapter;

import java.util.Collection;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.project.simi.domain.consent.domain.UserConsent;
import com.project.simi.domain.consent.repository.UserConsentCommandRepository;
import com.project.simi.domain.consent.repository.UserConsentJpaRepository;

@Repository
@RequiredArgsConstructor
public class UserConsentCommandRepositoryAdapter implements UserConsentCommandRepository {
    private final UserConsentJpaRepository userConsentJpaRepository;

    @Override
    public UserConsent save(UserConsent userConsent) {
        return userConsentJpaRepository.save(userConsent);
    }

    @Override
    public Collection<UserConsent> saveAll(Collection<UserConsent> userConsents) {
        return userConsentJpaRepository.saveAll(userConsents);
    }
}
