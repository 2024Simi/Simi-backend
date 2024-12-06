package com.project.simi.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

import com.project.simi.domain.auth.enums.LoginFlagEnum;

public class LoginDto {

    public record Request(@NotBlank String loginId, String password) {}

    public record Response(String accessToken, String refreshToken, Long userId) {}

    public record RefreshRequest(@NotBlank String refreshToken) {}

    public record LoginResponse(
            String accessToken, String refreshToken, Long userId, LoginFlagEnum loginFlag) {

        public LoginResponse(Response tokenResponse, LoginFlagEnum loginFlagEnum) {
            this(
                    tokenResponse.accessToken,
                    tokenResponse.refreshToken,
                    tokenResponse.userId,
                    loginFlagEnum);
        }
    }
}
