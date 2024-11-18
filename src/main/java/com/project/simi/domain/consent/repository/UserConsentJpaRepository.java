package com.project.simi.domain.consent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.simi.domain.consent.domain.UserConsent;

public interface UserConsentJpaRepository extends JpaRepository<UserConsent, String> {}
