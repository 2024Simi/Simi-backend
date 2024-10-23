package com.project.simi.domain.diary.repository.command.adapter;

import static com.project.simi.domain.diary.domain.QDiary.diary;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.project.simi.common.exception.NotFoundException;
import com.project.simi.common.exception.code.NotFoundResourceCode;
import com.project.simi.domain.diary.domain.Diary;
import com.project.simi.domain.diary.dto.DiaryCalendarDto;
import com.project.simi.domain.diary.repository.DiaryJpaRepository;
import com.project.simi.domain.diary.repository.command.DiaryQueryRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@RequiredArgsConstructor
public class DiaryQueryRepositoryAdapter implements DiaryQueryRepository {

    private final DiaryJpaRepository diaryJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<DiaryCalendarDto> getDiariesByDate(
            Long userId, LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(Projections.constructor(DiaryCalendarDto.class, diary.id, diary.createdAt))
                .from(diary)
                .where(
                        diary.createdBy.eq(userId),
                        diary.createdAt.between(
                                startDate.atStartOfDay(ZoneId.of("Asia/Seoul")).toInstant(),
                                endDate.atStartOfDay(ZoneId.of("Asia/Seoul")).toInstant()))
                .fetch();
    }

    @Override
    public Diary getById(Long diaryId) {
        return diaryJpaRepository
                .findById(diaryId)
                .orElseThrow(() -> new NotFoundException(NotFoundResourceCode.DIARY));
    }
}
