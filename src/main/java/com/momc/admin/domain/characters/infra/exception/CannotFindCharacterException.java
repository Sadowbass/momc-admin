package com.momc.admin.domain.characters.infra.exception;

public class CannotFindCharacterException extends RuntimeException{

    public CannotFindCharacterException(String message) {
        super(message);
    }
}
