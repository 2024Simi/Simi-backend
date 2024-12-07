package com.project.simi.domain.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.project.simi.domain.common.dto.DescriptionEnum;

@Getter
@AllArgsConstructor
public enum UserStatusEnum implements DescriptionEnum {
    SIGN_UP("회원가입"),
    NICKNAME_PENDING("약관 동의까지 완료"),
    LOGIN("로그인"),
    WITHDRAWAL("탈퇴"),
    ;

    private final String description;
}
