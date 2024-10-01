package com.project.simi.common.exception;

import lombok.Getter;

import com.project.simi.common.exception.code.ResultCodeProvider;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException() {
        super(new UnauthorizedCode());
    }

    @Getter
    public static class UnauthorizedCode implements ResultCodeProvider {
        private final String code = "NOT_FOUND_AUTHENTICATED_USER";
        private final String message = "로그인 정보를 찾을 수 없습니다.";
    }
}
