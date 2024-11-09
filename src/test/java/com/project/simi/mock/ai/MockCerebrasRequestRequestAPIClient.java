package com.project.simi.mock.ai;

import com.project.simi.domain.ai.cerebras.api.ICerebrasRequestAPIClient;
import com.project.simi.domain.ai.dto.ChatMessage;
import com.project.simi.domain.ai.dto.ChatRequest;
import com.project.simi.domain.ai.dto.ChatResponse;
import com.project.simi.domain.ai.dto.ChatResponse.TimeInfo;
import com.project.simi.domain.ai.dto.ChatResponse.Usage;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MockCerebrasRequestRequestAPIClient implements ICerebrasRequestAPIClient {

    @Override
    public CompletableFuture<ChatResponse> getChatResponse(ChatRequest chatRequest) {
        return CompletableFuture.completedFuture(
            createMockChatResponse()
        );
    }
    public static ChatResponse createMockChatResponse() {
        // TimeInfo mock
        ChatResponse.TimeInfo timeInfo = new TimeInfo(0.123, 0.234, 0.345, 0.456, System.currentTimeMillis());

        // Usage mock
        ChatResponse.Usage usage = new Usage(10, 20, 30);

        // ChatMessage mock
        ChatMessage message = ChatMessage.createOf("Hello, this is a mock message.", "user");

        // Choice mock
        ChatResponse.Choice choice = new ChatResponse.Choice("stop", 0, message);
        ChatResponse.Choice choice2 = new ChatResponse.Choice("stop", 1, message);
        List<ChatResponse.Choice> choices = List.of(choice, choice2);

        // ChatResponse mock
        return new ChatResponse(
            "mock-id",
            choices,
            System.currentTimeMillis(),
            "mock-model",
            "mock-system-fingerprint",
            "mock-object",
            usage,
            timeInfo
        );
    }
}
