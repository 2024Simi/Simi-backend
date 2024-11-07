package com.project.simi.domain.consent.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.project.simi.domain.common.dto.DescriptionEnum;

@Getter
@AllArgsConstructor
public enum ConsentType implements DescriptionEnum {
    PRIVATE_POLICY("개인정보 처리방침"),
    TERMS_OF_USE("이용약관"),
    MARKETING("마케팅 정보 수신 동의");

    private final String description;
}
