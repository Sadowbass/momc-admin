package com.momc.admin.infra.config.security.auth.exception;

public class NotLoginException extends RuntimeException{

    public NotLoginException(String message) {
        super(message);
    }
}
