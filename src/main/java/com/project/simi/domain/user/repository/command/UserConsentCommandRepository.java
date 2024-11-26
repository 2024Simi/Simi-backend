package com.project.simi.domain.user.repository.command;

import com.project.simi.domain.user.domain.UserConsent;

public interface UserConsentCommandRepository {
    UserConsent save(UserConsent userConsent);
}
