package com.project.simi.auth.controller;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.simi.auth.dto.LoginDto;
import com.project.simi.auth.dto.LogoutDto;
import com.project.simi.auth.resolver.Authenticated;
import com.project.simi.auth.service.LoginService;
import com.project.simi.common.base.ApiResult;
import com.project.simi.user.dto.RequestUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Validated
public class AuthController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ApiResult<LoginDto.Response> login(@RequestBody @Valid LoginDto.Request loginRequest) {
        return ApiResult.ok(loginService.login(loginRequest));
    }

    @PostMapping("/logout")
    public ApiResult<?> logout(
            @Authenticated RequestUser requestUser, // 중복 로그인 허용
            @RequestBody @Valid LogoutDto.Request logoutRequest) {
        loginService.logout(requestUser.getId(), logoutRequest.refreshToken());

        return ApiResult.ok();
    }
}