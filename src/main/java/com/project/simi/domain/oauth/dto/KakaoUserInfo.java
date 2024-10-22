package com.project.simi.domain.oauth.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class KakaoUserInfo extends Oauth2UserInfo {

    public KakaoUserInfo(Map<String, Object> attributes) {
        super(attributes);
        KakaoUserInfo converted = objectMapper.convertValue(attributes, KakaoUserInfo.class);
        this.id = converted.getId();
        this.kakaoAccount = converted.getKakaoAccount();
        this.properties = converted.getProperties();
    }

    private final long id;
    private final KakaoAccount kakaoAccount;
    private final Properties properties;

    @AllArgsConstructor
    @Getter
    public static class KakaoAccount {

        private boolean profileNeedsAgreement;
        private Profile profile;
        private boolean emailNeedsAgreement;
        private boolean isEmailValid;
        private boolean isEmailVerified;
        private String email;
        private boolean nameNeedsAgreement;
        private String name;
        private boolean ageRangeNeedsAgreement;
        private String ageRange;
        private boolean birthdayNeedsAgreement;
        private String birthday;
        private boolean genderNeedsAgreement;
        private String gender;

        @AllArgsConstructor
        @Getter
        public static class Profile {

            private String nickname;
            private String thumbnailImageUrl;
            private String profileImageUrl;
            private boolean isDefaultImage;
            private boolean isDefaultNickname;

            // getters and setters
        }

        // getters and setters
    }

    @AllArgsConstructor
    @Getter
    public static class Properties {

        private String nickname;
        private String thumbnailImage;
        private String profileImage;
    }
}
