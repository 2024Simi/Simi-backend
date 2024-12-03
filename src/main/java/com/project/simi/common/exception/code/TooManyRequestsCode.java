package com.project.simi.common.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TooManyRequestsCode implements ResultCodeProvider {
    DAILY_LIMIT_EXCEEDED("하루 작성 가능한 최대 개수를 초과했습니다."),
    ;
    private final String message;
}
