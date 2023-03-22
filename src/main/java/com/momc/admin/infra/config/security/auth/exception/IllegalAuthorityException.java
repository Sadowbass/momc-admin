package com.momc.admin.infra.config.security.auth.exception;

public class IllegalAuthorityException extends RuntimeException{

    public IllegalAuthorityException(String message) {
        super(message);
    }
}
