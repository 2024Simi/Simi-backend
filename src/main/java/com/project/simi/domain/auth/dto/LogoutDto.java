package com.project.simi.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class LogoutDto {

    public record Request(@NotBlank String refreshToken) {}
}
