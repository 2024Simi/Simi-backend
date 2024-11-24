package com.project.simi.domain.user.dto;

/**
 * @link com.project.simi.domain.user.UserConsent
 */
public class UserConsentDto {
    public record Request(
            Boolean isAgreePrivatePolicy,
            Boolean isAgreeTermsOfService,
            Boolean isAgreeMarketing) {}
}
