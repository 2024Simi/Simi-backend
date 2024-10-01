package com.project.simi.domain.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.project.simi.domain.common.dto.DescriptionEnum;

@Getter
@AllArgsConstructor
public enum AuthoriryEnum implements DescriptionEnum {
    ROLE_ADMIN("관리자"),
    ROLE_DEFAULT("일반 사용자"),
    ROLE_PREMIUM("프리미엄 사용자");

    private final String description;
}
