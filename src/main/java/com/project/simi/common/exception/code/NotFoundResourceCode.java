package com.project.simi.common.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotFoundResourceCode {
    USER("유저"),
    DIARY("일기"),
    AIPROMPT("AI 프롬프트"),
    ;

    private final String message;
}
