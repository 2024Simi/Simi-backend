package com.project.simi.domain.ai.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ChatRequest {
    private List<ChatMessage> messages;
    private String model;
    private boolean stream;
    private double temperature;

    @JsonProperty("top_p")
    private double topP;

    @JsonProperty("max_tokens")
    private int maxTokens;

    // Constructors
    @Override
    public String toString() {
        return "ChatRequest{"
                + "messages="
                + messages
                + ", model='"
                + model
                + '\''
                + ", stream="
                + stream
                + ", temperature="
                + temperature
                + ", topP="
                + topP
                + ", maxTokens="
                + maxTokens
                + '}';
    }
}
