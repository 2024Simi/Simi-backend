package com.project.simi.domain.consent.service;

import java.util.List;

import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.simi.domain.consent.domain.UserConsent;
import com.project.simi.domain.consent.repository.UserConsentCommandRepository;
import com.project.simi.domain.consent.repository.UserConsentQueryRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class UserConsentCommandService {
    private final UserConsentCommandRepository userConsentCommandRepository;
    private final UserConsentQueryRepository userConsentQueryRepository;

    private List<UserConsent> findAllByUserId(Long userId) {
        return userConsentQueryRepository.findAllByUserId(userId);
    }
}
