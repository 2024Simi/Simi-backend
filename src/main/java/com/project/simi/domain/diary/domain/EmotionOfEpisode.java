package com.project.simi.domain.diary.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmotionOfEpisode implements Serializable {

    @Serial private static final long serialVersionUID = 1L;

    @JsonProperty("type")
    private EmotionType type;

    @JsonProperty("details")
    private List<String> details;

    @Override
    public String toString() {
        return "EmotionOfEpisode[" + "type=" + type + ", " + "details=" + details + ']';
    }
}
