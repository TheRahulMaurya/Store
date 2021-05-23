package com.erp.store.exception;

public class InvalidFilterException extends RuntimeException{
    public InvalidFilterException() {
        super();
    }

    public InvalidFilterException(String message) {
        super(message);
    }

    public InvalidFilterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFilterException(Throwable cause) {
        super(cause);
    }

    protected InvalidFilterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
