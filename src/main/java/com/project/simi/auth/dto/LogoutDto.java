package com.project.simi.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class LogoutDto {

    public record Request(@NotBlank String refreshToken) {}
}
