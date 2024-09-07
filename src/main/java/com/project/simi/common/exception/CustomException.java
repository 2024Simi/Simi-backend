package com.project.simi.common.exception;

import com.project.simi.common.exception.code.ResultCodeProvider;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public abstract class CustomException extends RuntimeException {
    private final ResultCodeProvider resultCode;
    private final Object data;

    protected CustomException(final ResultCodeProvider resultCode) {
        super(resultCode.getMessage());

        this.resultCode = resultCode;
        this.data = null;
    }

    protected CustomException(final ResultCodeProvider resultCode, final Object data) {
        super(resultCode.getMessage());

        this.resultCode = resultCode;
        this.data = data;
    }

    protected CustomException(
        final ResultCodeProvider resultCode, final String specifiedErrorMessage) {
        super(resultCode.getMessage());

        this.resultCode =
            new ResultCodeProvider() {
                @Override
                public String getMessage() {
                    return specifiedErrorMessage;
                }

                @Override
                public String getCode() {
                    if (resultCode instanceof Enum<?>) {
                        return ((Enum<?>) resultCode).name();
                    }

                    return null;
                }
            };
        this.data = null;
    }

    protected CustomException(final ResultCodeProvider resultCode, final Throwable cause) {
        super(resultCode.getMessage(), cause);

        this.resultCode = resultCode;
        this.data = null;
    }

    protected CustomException(
        final ResultCodeProvider resultCode, final Object data, final Throwable cause) {
        super(resultCode.getMessage(), cause);

        this.resultCode = resultCode;
        this.data = data;
    }
}
