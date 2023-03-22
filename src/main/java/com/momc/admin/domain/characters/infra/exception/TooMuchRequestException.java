package com.momc.admin.domain.characters.infra.exception;

public class TooMuchRequestException extends RuntimeException{

    public TooMuchRequestException(String message) {
        super(message);
    }
}
