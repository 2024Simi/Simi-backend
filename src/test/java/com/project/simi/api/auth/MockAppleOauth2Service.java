package com.project.simi.api.auth;

import com.project.simi.domain.auth.dto.AppleUserOIDC;
import com.project.simi.domain.auth.service.AppleOauth2Service;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockAppleOauth2Service extends AppleOauth2Service {
    @Bean
    public AppleOauth2Service appleOauth2Service() {
        return new MockAppleOauth2Service();
    }

    @Override
    public AppleUserOIDC getUserInfoAndVerify(String idToken) throws Exception {
        System.out.println("MockAppleOauth2Service.getUserInfoAndVerify" );
        return new AppleUserOIDC(
                "your_iss_value",           // iss
                "your_sub_value",           // sub
                "your_aud_value",           // aud
                1622548800L,                // iat (예시 Unix 타임스탬프)
                1622552400L,                // exp (예시 Unix 타임스탬프)
                "your_email_value",         // email
                true,                       // emailVerified
                true,                       // isPrivateEmail
                1                           // realUserStatus
        );
    }
}
