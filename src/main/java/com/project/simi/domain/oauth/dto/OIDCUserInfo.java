package com.project.simi.domain.oauth.dto;

public interface OIDCUserInfo {
    String getSub();

    String getEmail();

    String getNickname();

    String getPicture();
}
