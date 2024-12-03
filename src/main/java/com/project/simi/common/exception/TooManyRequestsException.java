package com.project.simi.common.exception;

import com.project.simi.common.exception.code.TooManyRequestsCode;

public class TooManyRequestsException extends CustomException {

    public TooManyRequestsException(TooManyRequestsCode resultCode) {
        super(resultCode);
    }

    public TooManyRequestsException(TooManyRequestsCode resultCode, Object data) {
        super(resultCode, data);
    }
}
