package com.project.simi.domain.user.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.simi.common.base.ApiResult;
import com.project.simi.domain.auth.resolver.Authenticated;
import com.project.simi.domain.user.dto.RequestUser;
import com.project.simi.domain.user.dto.UserDto;
import com.project.simi.domain.user.service.UserQueryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserQueryService userQueryService;

    @GetMapping("/me")
    public ApiResult<UserDto> getByUserId(@Authenticated RequestUser requestUser) {
        return ApiResult.ok(userQueryService.getUserById(requestUser.getId()));
    }
}
