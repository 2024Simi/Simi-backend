package com.project.simi.domain.ai.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.simi.common.base.ApiResult;
import com.project.simi.domain.ai.cerebras.api.CerebrasRequestAPIClient;
import com.project.simi.domain.ai.dto.ChatRequest;
import com.project.simi.domain.ai.dto.ChatResponse;
import com.project.simi.domain.ai.service.AIPromptCommandService;
import com.project.simi.domain.ai.service.AIRequestHelperService;
import com.project.simi.domain.common.dto.IdResponseBase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/ai")
public class AdminAIController {

    private final CerebrasRequestAPIClient cerebrasRequestAPIClient;
    private final AIPromptCommandService aiPromptCommandService;
    private final AIRequestHelperService aiRequestHelperService;

    @PostMapping("/chat/completions")
    public ApiResult<ChatResponse> chat(@RequestBody ChatRequest request) {
        return ApiResult.ok(cerebrasRequestAPIClient.getChatResponse(request).join());
    }

    @GetMapping("/chat/completions")
    public ApiResult<ChatResponse> chat(@RequestParam String prompt) {
        return ApiResult.ok(aiRequestHelperService.requestChatResponse(prompt));
    }

    /*
     * TODO 1. AI Prompt 관리 , 생성 및 수정 , Default 설정 API
     * */

    @PostMapping("/prompt")
    public ApiResult<IdResponseBase> createAIPrompt(@RequestBody ChatRequest request) {
        return ApiResult.ok(aiPromptCommandService.createAIPrompt(request));
    }
}
