package com.project.simi.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDto {

    public record Request(@NotBlank String loginId, String password) {}

    public record Response(String accessToken, String refreshToken, Long userId) {}

    public record RefreshRequest(@NotBlank String refreshToken) {}
}
