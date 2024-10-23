package com.project.simi.domain.diary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.project.simi.domain.common.dto.DescriptionEnum;

@Getter
@AllArgsConstructor
public enum EmotionType implements DescriptionEnum {
    HAPPY("행복"),
    SAD("슬픔"),
    ANGRY("분노"),
    FEAR("두려움"),
    DISGUST("불쾌"),
    SOMEHOW("그럭저럭");

    private final String name;

    @Override
    public String getDescription() {
        return name;
    }
}
