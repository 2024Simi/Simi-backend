package com.project.simi.domain.user.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.simi.common.base.ApiResult;
import com.project.simi.domain.auth.resolver.Authenticated;
import com.project.simi.domain.user.dto.RequestUser;
import com.project.simi.domain.user.dto.UserConsentDto;
import com.project.simi.domain.user.dto.UserDto;
import com.project.simi.domain.user.dto.UserDto.UserResponseDto;
import com.project.simi.domain.user.service.UserCommandService;
import com.project.simi.domain.user.service.UserConsentCommandService;
import com.project.simi.domain.user.service.UserQueryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;
    private final UserConsentCommandService userConsentCommandService;

    @GetMapping("/me")
    public ApiResult<UserResponseDto> getByUserId(@Authenticated RequestUser requestUser) {
        return ApiResult.ok(userQueryService.getUserById(requestUser.getId()));
    }

    @PatchMapping("/nickname")
    public ApiResult<?> updateNickname(
            @Authenticated RequestUser requestUser,
            @RequestBody UserDto.UserRequestDto.UpdateNickname request) {
        userCommandService.updateNickname(requestUser.getId(), request.getNickname());
        return ApiResult.ok();
    }

    @Deprecated
    @PatchMapping("/private-policy")
    public ApiResult<?> agreePrivatePolicy(@Authenticated RequestUser requestUser) {
        userCommandService.agreePrivatePolicy(requestUser.getId());
        return ApiResult.ok();
    }

    @PutMapping("/consent")
    public ApiResult<?> updateConsent(
            @Authenticated RequestUser requestUser, @RequestBody UserConsentDto.Request request) {
        userConsentCommandService.updateConsent(requestUser, request);

        return ApiResult.ok();
    }
}
