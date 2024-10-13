package com.project.simi.domain.diary.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EmotionOfEpisode(
        @JsonProperty("type") String type, @JsonProperty("details") List<String> details) {}
