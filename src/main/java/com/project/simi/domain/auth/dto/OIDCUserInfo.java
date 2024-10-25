package com.project.simi.domain.auth.dto;

public interface OIDCUserInfo {
    String getSub();

    String getEmail();

    String getNickname();

    String getPicture();
}
