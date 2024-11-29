package com.project.simi.domain.diary.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.simi.common.exception.TooManyRequestsException;
import com.project.simi.common.exception.code.TooManyRequestsCode;
import com.project.simi.domain.ai.service.AIRequestHelperService;
import com.project.simi.domain.diary.domain.Diary;
import com.project.simi.domain.diary.domain.EmotionOfEpisode;
import com.project.simi.domain.diary.dto.DiaryCalendarDto;
import com.project.simi.domain.diary.dto.DiaryDto;
import com.project.simi.domain.diary.dto.DiaryDto.DiaryCreateResponse;
import com.project.simi.domain.diary.dto.DiaryDto.DiaryRequest;
import com.project.simi.domain.diary.dto.DiaryDto.DiaryUpdateRequest;
import com.project.simi.domain.diary.dto.DiaryDto.EmotionOfEpisodeDto;
import com.project.simi.domain.diary.repository.command.DiaryCommandRepository;
import com.project.simi.domain.diary.repository.command.DiaryQueryRepository;
import com.project.simi.domain.user.dto.RequestUser;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryCommandRepository diaryCommandRepository;
    private final DiaryQueryRepository diaryQueryRepository;
    private final AIRequestHelperService aiRequestHelperService;

    public DiaryCreateResponse createDiary(RequestUser requestUser, DiaryRequest request) {
        // 작성한 일기가 있는지 확인. 있으면 오류, 없으면 생성
        if (diaryQueryRepository.existsByUserIdAndCreatedAt(requestUser.getId(), LocalDate.now())) {
            throw new TooManyRequestsException(TooManyRequestsCode.DAILY_LIMIT_EXCEEDED);
        }

        String empathyResponse =
                aiRequestHelperService.requestChatResponse(request.toString()).getChoices().stream()
                        .map(res -> res.getMessage().getContent())
                        .collect(Collectors.joining(" "));
        Long diaryId = saveDiary(request, empathyResponse);
        return new DiaryCreateResponse(diaryId, empathyResponse);
    }

    private Long saveDiary(DiaryRequest request, String empathyResponse) {
        return diaryCommandRepository
                .save(
                        Diary.createOf(
                                request.episode(),
                                request.thoughtOfEpisode(),
                                request.emotionOfEpisodes().stream()
                                        .map(this::createEmotionOfEpisode)
                                        .toList(),
                                request.resultOfEpisode(),
                                empathyResponse))
                .getId();
    }

    private EmotionOfEpisode createEmotionOfEpisode(EmotionOfEpisodeDto request) {
        return new EmotionOfEpisode(request.type(), request.details());
    }

    @Transactional(readOnly = true)
    public List<DiaryCalendarDto> getDiariesByDate(
            RequestUser requestUser, LocalDate startDate, LocalDate endDate) {
        return diaryQueryRepository.getDiariesByDate(requestUser.getId(), startDate, endDate);
    }

    private Diary getDiary(Long diaryId) {
        return diaryQueryRepository.getById(diaryId);
    }

    @Transactional(readOnly = true)
    public DiaryDto.Response getDiary(RequestUser requestUser, Long diaryId) {
        Diary diary = getDiary(diaryId);
        return DiaryDto.Response.createOf(diary);
    }

    public Long updateDiary(Long diaryId, DiaryUpdateRequest request) {
        Diary diary = getDiary(diaryId);
        diary.updateDiary(
                request.episode(),
                request.thoughtOfEpisode(),
                request.emotionOfEpisodes().stream().map(this::createEmotionOfEpisode).toList(),
                request.resultOfEpisode());
        return diary.getId();
    }
}
