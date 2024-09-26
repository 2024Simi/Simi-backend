package com.project.simi.domain.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.project.simi.domain.common.dto.DescriptionEnum;

@Getter
@AllArgsConstructor
public enum AIProvider implements DescriptionEnum {
    CEREBRAS("cerebras");

    private final String description;
}
