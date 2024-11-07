package com.project.simi.domain.consent.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.project.simi.domain.common.dto.DescriptionEnum;

@Getter
@AllArgsConstructor
public enum NotificationStatus implements DescriptionEnum {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    ;
    private final String description;
}
