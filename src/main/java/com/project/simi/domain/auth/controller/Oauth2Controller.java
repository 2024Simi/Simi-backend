package com.project.simi.domain.auth.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.simi.common.base.ApiResult;
import com.project.simi.domain.auth.dto.LoginDto.LoginResponse;
import com.project.simi.domain.auth.enums.AuthProviderEnum;
import com.project.simi.domain.auth.service.Oauth2Service;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth2")
public class Oauth2Controller {

    private final Oauth2Service oauth2Service;

    @PostMapping("/{provider}")
    public ApiResult<LoginResponse> oauth2Login(
            @PathVariable AuthProviderEnum provider,
            @Parameter(name = "IdToken", description = "Oauth2 사용자 ID 토큰", required = true)
                    @RequestHeader("IdToken")
                    String idToken) {
        return ApiResult.ok(oauth2Service.loginSignUp(provider, idToken));
    }
}
