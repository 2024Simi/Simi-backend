package com.project.simi.domain.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.project.simi.domain.common.dto.DescriptionEnum;

@Getter
@AllArgsConstructor
public enum UserStatusEnum implements DescriptionEnum {
    SIGN_UP("회원가입"),
    NICKNAME_PENDING("닉네임 등록 X"),
    ACTIVE("활성 상태"),
    EXPIRED("계정 만료 상태"),
    LOCKED("계정 정지 상태");

    private final String description;
}
