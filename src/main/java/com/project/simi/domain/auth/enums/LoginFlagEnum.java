package com.project.simi.domain.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.project.simi.domain.common.dto.DescriptionEnum;

@Getter
@AllArgsConstructor
public enum LoginFlagEnum implements DescriptionEnum {
    SIGN_IN("회원가입 완료"),
    NICKNAME_PENDING("약관 동의까지 완료"),
    LOGIN("로그인"),
    WITHDRAWAL("탈퇴"),
    ;

    private final String description;
}
