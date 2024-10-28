package com.project.simi.domain.ai.repository.adapter;

import static com.project.simi.domain.ai.domain.QAIPrompt.aIPrompt;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import org.springframework.stereotype.Repository;

import com.project.simi.common.exception.NotFoundException;
import com.project.simi.common.exception.code.NotFoundResourceCode;
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
    @Delegate private final AIPromptJpaRepository jpaRepository;

    @Override
    public AIPrompt save(AIPrompt aiPrompt) {
        return jpaRepository.save(aiPrompt);
    }

    @Override
    public AIPrompt getDefaultAIPrompt() {
        return Optional.ofNullable(
                        queryFactory
                                .selectFrom(aIPrompt)
                                .where(aIPrompt.isDefault.isTrue())
                                .orderBy(aIPrompt.updatedAt.desc())
                                .limit(1)
                                .fetchOne())
                .orElseThrow(() -> new NotFoundException(NotFoundResourceCode.AIPROMPT));
    }
}
