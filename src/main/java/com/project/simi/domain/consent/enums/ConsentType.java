package com.project.simi.domain.consent.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.project.simi.domain.common.dto.DescriptionEnum;

@Getter
@AllArgsConstructor
public enum ConsentType implements DescriptionEnum {
    TERMS_OF_SERVICE("서비스 이용약관"),
    MARKETING("마케팅"),
    PRIVACY("개인정보");
    private final String description;
}
