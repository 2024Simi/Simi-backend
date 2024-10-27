package com.project.simi.api.auth;

import com.project.simi.domain.auth.dto.KakaoUserOIDC;
import com.project.simi.domain.auth.service.KakaoOauth2Service;
import java.util.List;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockKaKaoOauth2Service extends KakaoOauth2Service {
    @Bean
    public KakaoOauth2Service kakaoOauth2Service() {
        return new MockKaKaoOauth2Service();
    }

    @Override
    public KakaoUserOIDC getUserInfoAndVerify(String idToken) throws Exception {
        System.out.println("MockKaKaoOauth2Service.getUserInfoAndVerify");
        return  new KakaoUserOIDC(
            List.of("your_aud_value"),  // aud
            "your_sub_value",           // sub
            1622548800L,                // authTime (예시 Unix 타임스탬프)
            "your_iss_value",           // iss
            "your_nickname_value",      // nickname
            1622552400L,                // exp (예시 Unix 타임스탬프)
            1622548800L,                // iat (예시 Unix 타임스탬프)
            "http://your_picture_value",       // picture
            "your_email_value@email.com"          // email
        );
    }


}
