package com.project.simi.domain.diary.repository.command;

import java.time.LocalDate;
import java.util.List;

import com.project.simi.domain.diary.domain.Diary;
import com.project.simi.domain.diary.dto.DiaryCalendarDto;

public interface DiaryQueryRepository {
    List<DiaryCalendarDto> getDiariesByDate(Long userId, LocalDate startDate, LocalDate endDate);

    Diary getById(Long diaryId);
}
