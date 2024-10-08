package com.project.simi.domain.diary.repository.command;

import com.project.simi.domain.diary.domain.Diary;

public interface DiaryCommandRepository {
    Diary save(Diary diary);
}
