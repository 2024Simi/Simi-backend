package com.project.simi.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.project.simi.domain.auth.enums.AuthProviderEnum;
import com.project.simi.domain.common.dto.IdResponseBase;
import com.project.simi.domain.user.domain.User;

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
        private final Boolean isPrivatePolicyAgreed;
        private final Boolean isProfileSettings;

        private UserResponseDto(
                Long id,
                String loginId,
                String profileImageUrl,
                String nickname,
                AuthProviderEnum provider,
                Boolean isPrivatePolicyAgreed,
                Boolean isProfileSettings) {
            super(id);
            this.loginId = loginId;
            this.profileImageUrl = profileImageUrl;
            this.nickname = nickname;
            this.provider = provider;
            this.isPrivatePolicyAgreed = isPrivatePolicyAgreed;
            this.isProfileSettings = isProfileSettings;
        }

        public static UserResponseDto createOf(User user) {
            return new UserResponseDto(
                    user.getId(),
                    user.getLoginId(),
                    user.getProfileImageUrl(),
                    user.getNickname(),
                    user.getProvider(),
                    user.getIsPrivatePolicyAgreed(),
                    user.getIsProfileSettings());
        }
    }
}
