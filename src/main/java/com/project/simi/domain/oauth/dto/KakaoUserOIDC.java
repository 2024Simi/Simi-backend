package com.project.simi.domain.oauth.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserOIDC implements OIDCUserInfo {
    private List<String> aud;
    private String sub;
    private long authTime;
    private String iss;
    private String nickname;
    private long exp;
    private long iat;
    private String picture;
    private String email;
}
