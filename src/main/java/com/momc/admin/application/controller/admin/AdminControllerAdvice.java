package com.momc.admin.application.controller.admin;

import com.momc.admin.application.controller.common.ApiResponse;
import com.momc.admin.application.controller.common.ApiResponseFactory;
import com.momc.admin.infra.config.security.auth.exception.IllegalAuthorityException;
import com.momc.admin.infra.config.security.auth.exception.NotLoginException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.momc.admin.application.controller.admin")
public class AdminControllerAdvice {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({IllegalAuthorityException.class})
    public ApiResponse illegalAuthorityExceptionHandler(IllegalAuthorityException e) {
        return ApiResponseFactory.custom()
                .rspCode(HttpStatus.FORBIDDEN)
                .rspMessage(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotLoginException.class)
    public ApiResponse notLoginExceptionHandler(NotLoginException e) {
        return ApiResponseFactory.custom()
                .rspCode(HttpStatus.UNAUTHORIZED)
                .rspMessage(e.getMessage())
                .build();
    }
}
