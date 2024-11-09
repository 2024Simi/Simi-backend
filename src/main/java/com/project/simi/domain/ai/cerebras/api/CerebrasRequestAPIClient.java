package com.project.simi.domain.ai.cerebras.api;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.project.simi.domain.ai.dto.ChatRequest;
import com.project.simi.domain.ai.dto.ChatResponse;

@Slf4j
@Component
public class CerebrasRequestAPIClient implements ICerebrasRequestAPIClient {

    private static final String CEREBRAS_API_URL = "https://api.cerebras.ai/v1/chat/completions";
    private static final RestClient CLIENT = RestClient.builder().baseUrl(CEREBRAS_API_URL).build();
    private final Executor executor;
    private final RetryTemplate baseRetryTemplate;

    @Value("${ai.cerebras.token}")
    private String apiKey;

    public CerebrasRequestAPIClient(
            @Qualifier("virtualThreadExecutor") Executor executor,
            @Qualifier("baseRetryTemplate") RetryTemplate baseRetryTemplate) {
        this.executor = executor;
        this.baseRetryTemplate = baseRetryTemplate;
    }

    public CompletableFuture<ChatResponse> getChatResponse(ChatRequest chatRequest) {
        return CompletableFuture.supplyAsync(
                        () ->
                                baseRetryTemplate.execute(
                                        context -> {
                                            return CLIENT.post()
                                                    .body(chatRequest)
                                                    .headers(
                                                            headers -> {
                                                                headers.setBearerAuth(apiKey);
                                                                headers.setContentType(
                                                                        MediaType.valueOf(
                                                                                "application/json"));
                                                            })
                                                    .retrieve()
                                                    .body(ChatResponse.class);
                                        }),
                        executor)
                .thenApply(
                        chatResponse -> {
                            log.info("ChatResponse: {}", chatResponse);
                            return chatResponse;
                        })
                .exceptionally(
                        throwable -> {
                            log.error("Error: {}", throwable.getMessage());
                            return null;
                        })
                .toCompletableFuture();
    }
}
