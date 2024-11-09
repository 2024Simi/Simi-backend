package com.project.simi.domain.ai.cerebras.api;

import java.util.concurrent.CompletableFuture;

import com.project.simi.domain.ai.dto.ChatRequest;
import com.project.simi.domain.ai.dto.ChatResponse;

public interface ICerebrasRequestAPIClient {
    CompletableFuture<ChatResponse> getChatResponse(ChatRequest chatRequest);
}
