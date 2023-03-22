package com.momc.admin.domain.admin.exception;

public class DuplicateAdminIdException extends RuntimeException {

    public DuplicateAdminIdException(String message) {
        super(message);
    }
}
