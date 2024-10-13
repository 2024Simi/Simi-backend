package com.project.simi.domain.diary.domain;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class EmotionOfEpisode {
    @JsonProperty("type")
    private String type; // 감정의 유형

    @JsonProperty("details")
    private List<String> details; // 세부적인 감정 리스트

    public EmotionOfEpisode(String type, List<String> details) {
        this.type = type;
        this.details = details;
    }
}
