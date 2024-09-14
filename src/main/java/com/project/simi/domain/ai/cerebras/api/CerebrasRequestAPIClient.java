package com.project.simi.domain.ai.cerebras.api;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.project.simi.domain.ai.dto.ChatRequest;
import com.project.simi.domain.ai.dto.ChatResponse;

@Component
public class CerebrasRequestAPIClient {

    private static final String CEREBRAS_API_URL = "https://api.cerebras.ai/v1/chat/completions";
    private static final RestClient.Builder restClientBuilder =
            RestClient.builder().baseUrl(CEREBRAS_API_URL);
    private final Executor executor;

    @Value("${ai.cerebras.token}")
    private String apiKey;

    public CerebrasRequestAPIClient(@Qualifier("virtualThreadExecutor") Executor executor) {
        this.executor = executor;
    }

    public CompletableFuture<ChatResponse> getChatResponse(ChatRequest chatRequest) {
        return CompletableFuture.supplyAsync(
                () ->
                        restClientBuilder
                                .build()
                                .post()
                                .body(chatRequest)
                                .headers(
                                        headers -> {
                                            headers.setBearerAuth(apiKey);
                                            headers.setContentType(
                                                    MediaType.valueOf("application/json"));
                                        })
                                .retrieve()
                                .body(ChatResponse.class),
                executor);
    }
}
