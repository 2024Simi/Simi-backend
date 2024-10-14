package com.project.simi.domain.diary.service;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.simi.domain.diary.domain.Diary;
import com.project.simi.domain.diary.domain.EmotionOfEpisode;
import com.project.simi.domain.diary.domain.EmotionType;
import com.project.simi.domain.diary.dto.DiaryCalendarDto;
import com.project.simi.domain.diary.dto.DiaryDto.DiaryCreateResponse;
import com.project.simi.domain.diary.dto.DiaryDto.DiaryRequest;
import com.project.simi.domain.diary.repository.command.DiaryCommandRepository;
import com.project.simi.domain.diary.repository.command.DiaryQueryRepository;
import com.project.simi.domain.user.dto.RequestUser;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryCommandRepository diaryCommandRepository;
    private final DiaryQueryRepository diaryQueryRepository;

    public DiaryCreateResponse createDiary(DiaryRequest request) {
        // 지피티에게 물어봐서 결과 뽑기
        String empathyResponse = "지피티에게 물어봐서 결과 뽑기";

        // 다이어리 저장
        Long diaryId = saveDiary(request, empathyResponse);
        return new DiaryCreateResponse(diaryId, empathyResponse);
    }

    private Long saveDiary(DiaryRequest request, String empathyResponse) {
        return diaryCommandRepository
                .save(
                        Diary.createOf(
                                request.episode(),
                                request.thoughtOfEpisode(),
                                createEmotionOfEpisode(request),
                                request.resultOfEpisode(),
                                empathyResponse))
                .getId();
    }

    private EmotionOfEpisode createEmotionOfEpisode(DiaryRequest request) {
        return new EmotionOfEpisode(
                EmotionType.valueOf(request.emotionOfEpisode().type()),
                request.emotionOfEpisode().details());
    }

    public List<DiaryCalendarDto> getDiariesByDate(
            RequestUser requestUser, LocalDate startDate, LocalDate endDate) {
        return diaryQueryRepository.getDiariesByDate(requestUser.getId(), startDate, endDate);
    }
}
