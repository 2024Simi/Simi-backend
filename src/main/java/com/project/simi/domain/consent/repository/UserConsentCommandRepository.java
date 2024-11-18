package com.project.simi.domain.consent.repository;

import java.util.Collection;

import com.project.simi.domain.consent.domain.UserConsent;

public interface UserConsentCommandRepository {
    UserConsent save(UserConsent userConsent);

    Collection<UserConsent> saveAll(Collection<UserConsent> userConsents);
}
