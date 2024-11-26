package com.project.simi.domain.user.repository.query;

import java.util.Collection;
import java.util.Optional;

import com.project.simi.domain.user.domain.UserConsent;

public interface UserConsentQueryRepository {
    Optional<UserConsent> findByUserId(Long userId);

    Collection<UserConsent> findAll();
}
