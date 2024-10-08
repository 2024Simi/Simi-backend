package com.project.simi.domain.diary.repository.command.adapter;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.project.simi.domain.diary.domain.Diary;
import com.project.simi.domain.diary.repository.DiaryJpaRepository;
import com.project.simi.domain.diary.repository.command.DiaryCommandRepository;

@Repository
@RequiredArgsConstructor
public class DiaryCommandRepositoryAdapter implements DiaryCommandRepository {
    private final DiaryJpaRepository diaryJpaRepository;

    @Override
    public Diary save(Diary diary) {
        return diaryJpaRepository.save(diary);
    }
}
