package com.project.simi.common.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode implements ResultCodeProvider {
    LOGIN_FAILED("로그인에 실패하였습니다."),
    CAN_NOT_DELETE("삭제할 수 없습니다."),
    ACCESS_DENIED("권한이 없습니다."),
    VALIDATION_FAILED("유효성 검사에 실패하였습니다.");
    private final String message;
}
