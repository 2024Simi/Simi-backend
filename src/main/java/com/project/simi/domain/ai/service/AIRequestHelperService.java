package com.project.simi.domain.ai.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.simi.domain.ai.cerebras.api.CerebrasRequestAPIClient;
import com.project.simi.domain.ai.cerebras.dto.CerebrasAIModel;
import com.project.simi.domain.ai.dto.ChatMessage;
import com.project.simi.domain.ai.dto.ChatRequest;
import com.project.simi.domain.ai.dto.ChatResponse;
import com.project.simi.domain.ai.repository.AIPromptQueryRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AIRequestHelperService {
    private final CerebrasRequestAPIClient cerebrasRequestAPIClient;
    private final AIPromptQueryRepository aiPromptQueryRepository;

    public ChatResponse requestChatResponse(String prompt) {
        ChatRequest chatRequest = aiPromptQueryRepository.getDefaultAIPrompt().getChatRequest();
        String aiModel = chatRequest.getModel();
        if (CerebrasAIModel.values().stream()
                .anyMatch(model -> model.getDescription().equals(aiModel))) {
            chatRequest.getMessages().add(ChatMessage.createOf(prompt, "user"));
            return cerebrasRequestAPIClient.getChatResponse(chatRequest).join();
        }
        throw new IllegalArgumentException("Invalid AI Model");
    }
}
