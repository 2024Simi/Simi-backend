package com.project.simi.domain.diary.dto;

import java.util.List;

public class DiaryDto {
    public record DiaryRequest(
            String episode,
            String thoughtOfEpisode,
            EmotionDto emotionOfEpisode,
            String resultOfEpisode,
            String empathyResponse) {}

    public record DiaryCreateResponse(Long diaryId, String empathyResponse) {}

    public record EmotionDto(String type, List<String> details) {}
}
