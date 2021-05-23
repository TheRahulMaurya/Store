package com.erp.store.exception;

public class ExcelImportException extends RuntimeException{
    public ExcelImportException() {
        super();
    }

    public ExcelImportException(String message) {
        super(message);
    }

    public ExcelImportException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelImportException(Throwable cause) {
        super(cause);
    }

    protected ExcelImportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
