package com.momc.admin.domain.common.exception;

public abstract class MomcViewException extends RuntimeException {

    protected MomcViewException(String message) {
        super(message);
    }
}
