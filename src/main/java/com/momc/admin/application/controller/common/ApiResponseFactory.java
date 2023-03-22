package com.momc.admin.application.controller.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

public final class ApiResponseFactory {

    private ApiResponseFactory() {
        throw new UnsupportedOperationException("cannot instantiate this class");
    }

    public static ApiResponse ok() {
        return ok(null);
    }

    public static ApiResponse ok(Object result) {
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), result);
    }

    public static ApiResponse badRequest() {
        return badRequest(null);
    }

    public static ApiResponse badRequest(Object result) {
        return new ApiResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), result);
    }

    public static ApiResponseCustomBuilder custom() {
        return new ApiResponseCustomBuilder();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ApiResponseCustomBuilder {

        private int rspCode;
        private String rspMessage;
        private Object result;

        public ApiResponseCustomBuilder rspCode(int rspCode) {
            this.rspCode = rspCode;
            return this;
        }

        public ApiResponseCustomBuilder rspCode(HttpStatus httpStatus) {
            this.rspCode = httpStatus.value();
            return this;
        }

        public ApiResponseCustomBuilder rspMessage(String rspMessage) {
            this.rspMessage = rspMessage;
            return this;
        }

        public ApiResponseCustomBuilder result(Object result) {
            this.result = result;
            return this;
        }

        public ApiResponse build() {
            return new ApiResponse(this.rspCode, this.rspMessage, this.result);
        }
    }
}
