package com.project.simi.domain.ai.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@AllArgsConstructor
public class ChatResponse {

    private String id;
    private List<Choice> choices;
    private long created;
    private String model;

    @JsonProperty("system_fingerprint")
    private String systemFingerprint;

    private String object;
    private Usage usage;

    @JsonProperty("time_info")
    private TimeInfo timeInfo;

    @Getter
    @AllArgsConstructor
    public static class TimeInfo {
        @JsonProperty("queue_time")
        private double queueTime;

        @JsonProperty("prompt_time")
        private double promptTime;

        @JsonProperty("completion_time")
        private double completionTime;

        @JsonProperty("total_time")
        private double totalTime;

        private long created;
    }

    @Getter
    @AllArgsConstructor
    public static class Usage {
        @JsonProperty("prompt_tokens")
        private int promptTokens;

        @JsonProperty("completion_tokens")
        private int completionTokens;

        @JsonProperty("total_tokens")
        private int totalTokens;
    }

    @Getter
    @AllArgsConstructor
    public static class Choice {
        @JsonProperty("finish_reason")
        private String finishReason;

        private int index;
        private ChatMessage message;
    }

    public String getAllContents() {
        return choices.stream()
                .map(choice -> choice.getMessage().getContent())
                .collect(Collectors.joining());
    }
}
