package com.project.simi.domain.user.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.project.simi.domain.auth.enums.AuthProviderEnum;
import com.project.simi.domain.common.dto.IdResponseBase;
import com.project.simi.domain.user.domain.User;
import com.project.simi.domain.user.domain.UserConsent;

public class UserDto {

    public static class UserRequestDto {

        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class UpdateNickname {
            private String nickname;
        }
    }

    @Getter
    public static class UserResponseDto extends IdResponseBase {

        private final String profileImageUrl;
        private final String loginId;
        private final String nickname;
        private final AuthProviderEnum provider;
        private final Boolean isAgreePrivatePolicy;
        private final LocalDateTime agreePrivatePolicyAt;
        private final Boolean isAgreeTermsOfService;
        private final LocalDateTime agreeTermsOfServiceAt;
        private final Boolean isAgreeMarketingInfo;
        private final LocalDateTime agreeMarketingInfoAt;

        private UserResponseDto(
                Long id,
                String loginId,
                String profileImageUrl,
                String nickname,
                AuthProviderEnum provider,
                boolean isAgreePrivatePolicy,
                LocalDateTime agreePrivatePolicyAt,
                boolean isAgreeTermsOfService,
                LocalDateTime agreeTermsOfServiceAt,
                boolean isAgreeMarketingInfo,
                LocalDateTime agreeMarketingInfoAt) {
            super(id);
            this.loginId = loginId;
            this.profileImageUrl = profileImageUrl;
            this.nickname = nickname;
            this.provider = provider;
            this.isAgreePrivatePolicy = isAgreePrivatePolicy;
            this.agreePrivatePolicyAt = agreePrivatePolicyAt;
            this.isAgreeTermsOfService = isAgreeTermsOfService;
            this.agreeTermsOfServiceAt = agreeTermsOfServiceAt;
            this.isAgreeMarketingInfo = isAgreeMarketingInfo;
            this.agreeMarketingInfoAt = agreeMarketingInfoAt;
        }

        public static UserResponseDto createOf(User user, UserConsent userConsent) {
            return new UserResponseDto(
                    user.getId(),
                    user.getLoginId(),
                    user.getProfileImageUrl(),
                    user.getNickname(),
                    user.getProvider(),
                    userConsent.getIsAgreePrivatePolicy(),
                    userConsent.getAgreePrivatePolicyAt(),
                    userConsent.getIsAgreeTermsOfService(),
                    userConsent.getAgreeTermsOfServiceAt(),
                    userConsent.getIsAgreeMarketing(),
                    userConsent.getAgreeMarketingAt());
        }
    }
}
