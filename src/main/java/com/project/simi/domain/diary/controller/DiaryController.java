package com.project.simi.domain.diary.controller;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.simi.common.base.ApiResult;
import com.project.simi.domain.auth.resolver.Authenticated;
import com.project.simi.domain.diary.dto.DiaryCalendarDto;
import com.project.simi.domain.diary.dto.DiaryDto;
import com.project.simi.domain.diary.dto.DiaryDto.DiaryCreateResponse;
import com.project.simi.domain.diary.dto.DiaryDto.DiaryRequest;
import com.project.simi.domain.diary.service.DiaryService;
import com.project.simi.domain.user.dto.RequestUser;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("diary")
    public ApiResult<DiaryCreateResponse> createDiary(@Valid @RequestBody DiaryRequest request) {
        DiaryCreateResponse response = diaryService.createDiary(request);
        return ApiResult.ok(response);
    }

    @GetMapping("diary")
    public ApiResult<List<DiaryCalendarDto>> getDiariesByDate(
            @Authenticated RequestUser requestUser,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @Valid @RequestParam LocalDate startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @Valid @RequestParam LocalDate endDate) {
        List<DiaryCalendarDto> response =
                diaryService.getDiariesByDate(requestUser, startDate, endDate);
        return ApiResult.ok(response);
    }

    @GetMapping("diary/{diaryId}")
    public ApiResult<DiaryDto.Response> getDiary(
            @Authenticated RequestUser requestUser, @PathVariable Long diaryId) {
        return ApiResult.ok(diaryService.getDiary(requestUser, diaryId));
    }
}
