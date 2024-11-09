package com.project.simi.domain.diary.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.validation.constraints.NotEmpty;

import com.project.simi.domain.diary.domain.Diary;
import com.project.simi.domain.diary.domain.EmotionOfEpisode;
import com.project.simi.domain.diary.domain.EmotionType;
import com.project.simi.domain.diary.validation.MaxEmotionCheck;
import com.project.simi.domain.diary.validation.UniqueEmotionTypeCheck;

public class DiaryDto {
    public record DiaryRequest(
            String episode,
            String thoughtOfEpisode,
            @NotEmpty @MaxEmotionCheck @UniqueEmotionTypeCheck
                    List<EmotionOfEpisodeDto> emotionOfEpisodes,
            String resultOfEpisode) {
        public List<EmotionOfEpisodeDto> getEmotionOfEpisodesNotDuplicatedByType() {
            return emotionOfEpisodes.stream()
                    .collect(
                            Collectors.toMap(
                                    EmotionOfEpisodeDto::type,
                                    dto -> dto,
                                    (existing, replacement) -> existing))
                    .values()
                    .stream()
                    .toList();
        }
    }

    public record DiaryCreateResponse(Long diaryId, String empathyResponse) {}

    public record DiaryUpdateRequest(
            String episode,
            String thoughtOfEpisode,
            @NotEmpty @MaxEmotionCheck @UniqueEmotionTypeCheck
                    List<EmotionOfEpisodeDto> emotionOfEpisodes,
            String resultOfEpisode) {}

    public record EmotionOfEpisodeDto(EmotionType type, List<String> details) {}

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    public static class Response {
        private Long diaryId;
        private String episode;
        private String thoughtOfEpisode;
        private List<EmotionOfEpisode> emotionOfEpisodes;
        private EmotionType primaryEmotion;
        private String resultOfEpisode;
        private String empathyResponse;

        public static Response createOf(Diary diary) {
            Response response = new Response();
            response.diaryId = diary.getId();
            response.episode = diary.getEpisode();
            response.thoughtOfEpisode = diary.getThoughtOfEpisode();
            response.emotionOfEpisodes = diary.getEmotionOfEpisodes().emotionOfEpisodes();
            response.primaryEmotion = diary.getPrimaryEmotion();
            response.resultOfEpisode = diary.getResultOfEpisode();
            response.empathyResponse = diary.getEmpathyResponse();
            return response;
        }
    }
}
