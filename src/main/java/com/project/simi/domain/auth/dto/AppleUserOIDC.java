package com.project.simi.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppleUserOIDC implements OIDCUserInfo {
    private String iss;
    private String sub;
    private String aud;
    private long iat;
    private long exp;
    private String email;
    private Boolean emailVerified;
    private Boolean isPrivateEmail;
    private Integer realUserStatus;

    @Override
    public String getNickname() {
        return "익명의 시미";
    }

    @Override
    public String getPicture() {
        return "";
    }
}
