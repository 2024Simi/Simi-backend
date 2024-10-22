package com.project.simi.domain.oauth.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.simi.common.base.ApiResult;
import com.project.simi.domain.auth.dto.LoginDto;
import com.project.simi.domain.auth.enums.AuthProviderEnum;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth2")
public class Oauth2Controller {

    @GetMapping("/{provider}")
    public ApiResult<LoginDto.Response> oauth2Login(@PathVariable AuthProviderEnum provider) {

        return null;
    }
}
