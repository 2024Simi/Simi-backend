package com.project.simi.domain.diary.dto;

public class DiaryDto {
    public record DiaryRequest(
            String episode,
            String thoughtOfEpisode,
            String emotionOfEpisode,
            String resultOfEpisode,
            String empathyResponse) {}

    public record DiaryCreateResponse(Long diaryId, String empathyResponse) {}
}
