package com.project.simi.domain.diary.dto;

import java.util.List;

public class DiaryDto {
    public record DiaryRequest(
            String episode,
            String thoughtOfEpisode,
            List<EmotionOfEpisodeDto> emotionOfEpisodes,
            String resultOfEpisode,
            String empathyResponse) {}

    public record DiaryCreateResponse(Long diaryId, String empathyResponse) {}

    public record EmotionOfEpisodeDto(String type, List<String> details) {}
}
