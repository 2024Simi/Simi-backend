package com.project.simi.domain.diary.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.hibernate.annotations.Comment;

import com.project.simi.domain.common.domain.AbstractJpaIdentityPersistable;

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
    @Column(name = "emotion_of_episode", length = 255, nullable = false)
    private String emotionOfEpisode;

    @Comment("결과")
    @Column(name = "result_of_episode", length = 255, nullable = false)
    private String resultOfEpisode;

    @Comment("GPT의 한마디")
    @Column(name = "empathy_response", length = 255, nullable = false)
    private String empathyResponse;

    public Diary createOf(
            String episode,
            String thoughtOfEpisode,
            String emotionOfEpisode,
            String resultOfEpisode,
            String empathyResponse) {
        Diary diary = new Diary();
        diary.episode = episode;
        diary.thoughtOfEpisode = thoughtOfEpisode;
        diary.emotionOfEpisode = emotionOfEpisode;
        diary.resultOfEpisode = resultOfEpisode;
        diary.empathyResponse = empathyResponse;
        return diary;
    }
}
