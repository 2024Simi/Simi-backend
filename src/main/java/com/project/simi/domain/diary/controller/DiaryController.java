package com.project.simi.domain.diary.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.simi.common.base.ApiResult;
import com.project.simi.domain.diary.dto.DiaryDto.DiaryRequest;
import com.project.simi.domain.diary.service.DiaryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/*")
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping("diary")
    public ApiResult<String> createDiary(@RequestBody DiaryRequest request) {
        String response = diaryService.createDiary(request);
        return ApiResult.ok(response);
    }
}
