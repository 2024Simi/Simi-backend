package com.project.simi.domain.consent.repository;

import java.util.List;

import com.project.simi.domain.consent.domain.UserConsent;

public interface UserConsentQueryRepository {
    List<UserConsent> findAllByUserId(Long userId);
}
