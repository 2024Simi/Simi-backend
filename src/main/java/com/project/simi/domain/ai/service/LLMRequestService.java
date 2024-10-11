package com.project.simi.domain.ai.service;

import java.util.concurrent.CompletableFuture;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.simi.domain.ai.cerebras.api.CerebrasRequestAPIClient;
import com.project.simi.domain.ai.domain.AIPrompt;
import com.project.simi.domain.ai.dto.ChatMessage;
import com.project.simi.domain.ai.dto.ChatRequest;
import com.project.simi.domain.ai.dto.ChatResponse;
import com.project.simi.domain.ai.repository.AIPromptQueryRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LLMRequestService {

    private final CerebrasRequestAPIClient cerebrasRequestAPIClient;
    private final AIPromptQueryRepository aiPromptQueryRepository;
    private final LLMLoggingCommandService llmLoggingCommandService;

    public String requestLLM(String prompt) {
        AIPrompt defaultAIPrompt = aiPromptQueryRepository.findDefaultPrompt();
        ChatRequest chatRequest = defaultAIPrompt.getChatRequest();
        addMessageToChatRequest(chatRequest, prompt);
        CompletableFuture<ChatResponse> chatResponseCompletableFuture =
                cerebrasRequestAPIClient
                        .getChatResponse(chatRequest)
                        .whenCompleteAsync(
                                (chatResponse, throwable) -> {
                                    if (throwable == null) {
                                        // 성공 시 로그 저장
                                        llmLoggingCommandService.saveLog(chatResponse);
                                    } else {
                                        // 실패 시 로그 저장
                                        llmLoggingCommandService.saveErrorLog(throwable);
                                    }
                                });

        return chatResponseCompletableFuture.join().getAllContents();
    }

    private void addMessageToChatRequest(ChatRequest chatRequest, String message) {
        chatRequest.getMessages().add(new ChatMessage(message, "user"));
    }
}
