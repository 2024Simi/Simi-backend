package com.project.simi.hanlder;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.project.simi.common.base.ApiResult;
import com.project.simi.common.base.ValidationErrorResponse;
import com.project.simi.common.exception.BusinessException;
import com.project.simi.common.exception.NotFoundException;
import com.project.simi.common.exception.TooManyRequestsException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // =================================================================================================================================================

    @ResponseStatus(code = NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResult<?> handleNotFoundException(final NotFoundException e) {
        log.error(e.getMessage(), e);

        return ApiResult.of(e);
    }

    @ResponseStatus(code = BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ApiResult<?> handleBusinessException(final BusinessException e) {
        log.error(e.getMessage(), e);
        ExceptionMDCBuilder.getStringStringMap(e);

        return ApiResult.of(e);
    }

    @ResponseStatus(code = TOO_MANY_REQUESTS)
    @ExceptionHandler(TooManyRequestsException.class)
    public ApiResult<?> handleTooManyRequestsException(final TooManyRequestsException e) {
        log.error(e.getMessage(), e);
        ExceptionMDCBuilder.getStringStringMap(e);

        return ApiResult.of(e);
    }

    // =================================================================================================================================================

    @ResponseStatus(code = INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResult<?> handleException(final Exception e) {
        log.error(e.getMessage(), e);
        ExceptionMDCBuilder.getStringStringMap(e);

        return ApiResult.badRequest("오류가 발생하였습니다. 관리자에게 문의하세요.");
    }

    @ResponseStatus(code = INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ApiResult<?> handleRuntimeException(final RuntimeException e) {
        log.error(e.getMessage(), e);
        ExceptionMDCBuilder.getStringStringMap(e);

        return ApiResult.badRequest("오류가 발생하였습니다. 관리자에게 문의하세요.");
    }

    @ResponseStatus(code = METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResult<?> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        ExceptionMDCBuilder.getStringStringMap(e);

        return ApiResult.badRequest(e.getMessage());
    }

    @ResponseStatus(code = BAD_REQUEST)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResult<?> handleNoHandlerFoundException(final NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        ExceptionMDCBuilder.getStringStringMap(e);

        return ApiResult.badRequest(e.getMessage());
    }

    @ResponseStatus(code = BAD_REQUEST)
    @ExceptionHandler(ServletRequestBindingException.class)
    public ApiResult<?> handleServletRequestBindingException(
            final ServletRequestBindingException e) {
        log.error(e.getMessage(), e);
        ExceptionMDCBuilder.getStringStringMap(e);

        return ApiResult.badRequest(e.getMessage());
    }

    @ResponseStatus(code = UNPROCESSABLE_ENTITY) // 422
    @ExceptionHandler(BindException.class)
    public ApiResult<?> handleBindException(final BindException e) {
        log.error(e.getMessage(), e);

        List<ValidationErrorResponse> validationErrors =
                e.getFieldErrors().stream().map(ValidationErrorResponse::of).toList();

        return ApiResult.badRequest(validationErrors);
    }

    @ResponseStatus(code = BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResult<?> handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        ExceptionMDCBuilder.getStringStringMap(e);

        return ApiResult.badRequest("요청값이 잘못되었습니다. 데이터 형식을 확인해주세요.");
    }

    @ResponseStatus(code = BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResult<?> handleMissingServletRequestParameterException(
            final MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        ExceptionMDCBuilder.getStringStringMap(e);

        return ApiResult.badRequest(
                List.of(ValidationErrorResponse.createNotNull(e.getParameterName())));
    }

    @ResponseStatus(code = BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResult<?> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage(), e);
        ExceptionMDCBuilder.getStringStringMap(e);

        return ApiResult.badRequest(List.of(ValidationErrorResponse.of(e)));
    }
}
