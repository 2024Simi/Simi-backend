package com.project.simi.domain.ai.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.project.simi.domain.ai.domain.AIPrompt;
import com.project.simi.domain.ai.dto.ChatRequest;
import com.project.simi.domain.ai.repository.AIPromptCommandRepository;
import com.project.simi.domain.ai.repository.AIPromptQueryRepository;
import com.project.simi.domain.common.dto.DefaultIdResponse;

@Service
@RequiredArgsConstructor
public class AIPromptCommandService {
    private final AIPromptQueryRepository aiPromptQueryRepository;
    private final AIPromptCommandRepository aiPromptCommandRepository;

    public DefaultIdResponse createAIPrompt(ChatRequest request) {
        AIPrompt aiPrompt = AIPrompt.createOf(request);
        AIPrompt save = aiPromptCommandRepository.save(aiPrompt);

        return new DefaultIdResponse(save.getId());
    }
}
