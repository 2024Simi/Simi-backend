package com.project.simi.domain.ai.repository.adapter;

import static com.project.simi.domain.ai.domain.QAIPrompt.aIPrompt;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.project.simi.domain.ai.domain.AIPrompt;
import com.project.simi.domain.ai.repository.AIPromptCommandRepository;
import com.project.simi.domain.ai.repository.AIPromptJpaRepository;
import com.project.simi.domain.ai.repository.AIPromptQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@RequiredArgsConstructor
public class AIPromptRepositoryAdapter
        implements AIPromptCommandRepository, AIPromptQueryRepository {
    private final JPAQueryFactory queryFactory;
    private final AIPromptJpaRepository jpaRepository;

    @Override
    public AIPrompt save(AIPrompt aiPrompt) {
        return jpaRepository.save(aiPrompt);
    }

    @Override
    public AIPrompt findDefaultPrompt() {
        return Optional.ofNullable(
                        queryFactory
                                .selectFrom(aIPrompt)
                                .where(aIPrompt.isDefault.isTrue())
                                .fetchOne())
                .orElseThrow(
                        // TODO : Exception 처리
                        () -> new IllegalArgumentException("Default prompt not found"));
    }
}
