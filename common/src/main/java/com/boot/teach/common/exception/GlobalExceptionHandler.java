package com.boot.teach.common.exception;

import com.boot.teach.common.response.ResponseEnum;
import com.boot.teach.common.response.ServerResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ServerResponseEntity operationError(AuthenticationException e){
        return ServerResponseEntity.fail(ResponseEnum.AUTHENTICATION_EXCEPTION,e.getMessage());
    }

    @ExceptionHandler(LoginException.class)
    public ServerResponseEntity argumentError(LoginException e){
        return ServerResponseEntity.fail(ResponseEnum.CREDENTIALS_EXPIRE,e.getMessage());
    }

    @ExceptionHandler(CaptchaException.class)
    public ServerResponseEntity captchaError(CaptchaException e){
        return ServerResponseEntity.fail(ResponseEnum.CAPTCHA_EXCEPTION,e.getMessage());
    }
}
