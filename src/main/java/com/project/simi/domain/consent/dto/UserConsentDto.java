package com.project.simi.domain.consent.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.project.simi.domain.consent.enums.ConsentType;

public class UserConsentDto {
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PutRequest {
        private List<ConsentRequest> consents;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ConsentRequest {
        private ConsentType consentType;
        private String consentVersion;
        private Boolean isAgreed;
    }
}
