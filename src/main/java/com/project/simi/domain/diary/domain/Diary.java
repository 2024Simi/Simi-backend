package com.project.simi.domain.diary.domain;

import java.util.HashMap;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import org.hibernate.annotations.Comment;

import com.project.simi.domain.common.domain.AbstractJpaIdentityPersistable;
import com.project.simi.domain.diary.domain.converter.EmotionOfEpisodeConverter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "simi_diary")
@AttributeOverride(name = "id", column = @Column(name = "diary_id"))
@Entity
public class Diary extends AbstractJpaIdentityPersistable {

    @Comment("사건")
    @Column(name = "episode", length = 255, nullable = false)
    private String episode;

    @Comment("생각")
    @Column(name = "thought_of_episode", length = 255, nullable = false)
    private String thoughtOfEpisode;

    @Comment("감정")
    @Convert(converter = EmotionOfEpisodeConverter.class)
    @Column(name = "emotion_of_episodes", nullable = false)
    private EmotionOfEpisodes emotionOfEpisodes;

    @Comment("메인 감정")
    @Enumerated(EnumType.STRING)
    @Column(name = "primary_emotion", nullable = false)
    private EmotionType primaryEmotion;

    @Comment("결과")
    @Column(name = "result_of_episode", length = 255, nullable = false)
    private String resultOfEpisode;

    @Comment("GPT의 한마디")
    @Column(name = "empathy_response", length = 255, nullable = false)
    private String empathyResponse;

    private EmotionType calculatePrimaryEmotion(List<EmotionOfEpisode> emotionOfEpisodes) {
        HashMap<EmotionType, Integer> emotionMap = new HashMap<>();
        for (EmotionOfEpisode emotionOfEpisode : emotionOfEpisodes) {
            EmotionType emotion = emotionOfEpisode.getType();
            emotionMap.merge(emotion, emotionOfEpisode.getDetails().size(), Integer::sum);
        }

        int maxFrequency = emotionMap.values().stream().max(Integer::compareTo).orElse(0);
        long maxCount = emotionMap.values().stream().filter(count -> count == maxFrequency).count();

        if (maxCount > 1) {
            return EmotionType.SOMEHOW;
        }

        return emotionMap.entrySet().stream()
                .filter(entry -> entry.getValue() == maxFrequency)
                .findFirst()
                .get()
                .getKey();
    }

    private void updatePrimaryEmotion(EmotionType primaryEmotion) {
        this.primaryEmotion = primaryEmotion;
    }

    public static Diary createOf(
            String episode,
            String thoughtOfEpisode,
            List<EmotionOfEpisode> emotionOfEpisodes,
            String resultOfEpisode,
            String empathyResponse) {
        Diary diary = new Diary();
        diary.episode = episode;
        diary.thoughtOfEpisode = thoughtOfEpisode;
        diary.emotionOfEpisodes = new EmotionOfEpisodes(emotionOfEpisodes);
        diary.resultOfEpisode = resultOfEpisode;
        diary.empathyResponse = empathyResponse;
        diary.updatePrimaryEmotion(diary.calculatePrimaryEmotion(emotionOfEpisodes));
        return diary;
    }

    public static Diary createOf(
            String episode,
            String thoughtOfEpisode,
            List<EmotionOfEpisode> emotionOfEpisodes,
            String resultOfEpisode,
            String empathyResponse,
            Long createdBy) {
        Diary diary =
                createOf(
                        episode,
                        thoughtOfEpisode,
                        emotionOfEpisodes,
                        resultOfEpisode,
                        empathyResponse);
        diary.createdBy = createdBy;
        return diary;
    }
}
