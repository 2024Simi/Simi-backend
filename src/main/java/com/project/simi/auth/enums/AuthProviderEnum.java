package com.project.simi.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.project.simi.domain.common.dto.DescriptionEnum;

@Getter
@AllArgsConstructor
public enum AuthProviderEnum implements DescriptionEnum {
    BASIC("기본 인증"),
    KAKAO("카카오 인증"),
    GOOGLE("구글 인증"),
    NAVER("네이버 인증"),
    APPLE("애플 인증"),
    ;
    private final String description;
}
