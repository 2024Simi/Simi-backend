package com.project.simi.domain.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.simi.domain.ai.domain.AIPrompt;

public interface AIPromptJpaRepository extends JpaRepository<AIPrompt, Long> {}
