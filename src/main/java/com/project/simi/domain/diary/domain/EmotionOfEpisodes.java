package com.project.simi.domain.diary.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public record EmotionOfEpisodes(List<EmotionOfEpisode> emotionOfEpisodes) implements Serializable {

    @Serial private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "EmotionOfEpisodes[" + "emotionOfEpisodes=" + emotionOfEpisodes + ']';
    }
}
