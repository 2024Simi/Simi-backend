package com.project.simi.domain.diary.service;

import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.simi.domain.diary.domain.Diary;
import com.project.simi.domain.diary.dto.DiaryDto.DiaryRequest;
import com.project.simi.domain.diary.repository.command.DiaryCommandRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryCommandRepository diaryCommandRepository;

    public String createDiary(DiaryRequest request) {
        // 지피티에게 물어봐서 결과 뽑기
        String empathyResponse = "지피티에게 물어봐서 결과 뽑기";

        // 다이어리 저장
        diaryCommandRepository.save(
                Diary.createOf(
                        request.episode(),
                        request.thoughtOfEpisode(),
                        request.emotionOfEpisode(),
                        request.resultOfEpisode(),
                        empathyResponse));
        return empathyResponse;
    }
}
