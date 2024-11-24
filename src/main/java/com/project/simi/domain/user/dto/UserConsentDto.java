package com.project.simi.domain.user.dto;

import jakarta.validation.constraints.NotNull;

/**
 * @link com.project.simi.domain.user.UserConsent
 */
public class UserConsentDto {
    public record Request(
            @NotNull Boolean isAgreePrivatePolicy,
            @NotNull Boolean isAgreeTermsOfService,
            @NotNull Boolean isAgreeMarketing) {}
}
