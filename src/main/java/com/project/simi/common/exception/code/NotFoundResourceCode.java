package com.project.simi.common.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotFoundResourceCode {
    USER("유저"),
    DIARY("일기"),
    ;

    private final String message;
}
