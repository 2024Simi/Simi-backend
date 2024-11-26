package com.project.simi.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.simi.domain.user.domain.UserConsent;

public interface UserConsentJpaRepository extends JpaRepository<UserConsent, Long> {}
