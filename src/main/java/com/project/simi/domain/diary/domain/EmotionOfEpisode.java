package com.project.simi.domain.diary.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EmotionOfEpisode(
        @JsonProperty("type") EmotionType type, @JsonProperty("details") List<String> details)
        implements Serializable {

    @Serial private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "EmotionOfEpisode[" + "type=" + type + ", " + "details=" + details + ']';
    }
}
