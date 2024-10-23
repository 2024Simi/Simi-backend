package com.project.simi.domain.diary.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EmotionOfEpisodes(
        @JsonProperty("emotionOfEpisodes") List<EmotionOfEpisode> emotionOfEpisodes)
        implements Serializable {

    @Serial private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "EmotionOfEpisodes[" + "emotionOfEpisodes=" + emotionOfEpisodes + ']';
    }
}
