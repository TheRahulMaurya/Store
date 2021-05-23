package com.erp.store.exception;

import com.erp.store.constant.ApiConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This is a global exception handler
 */
@ControllerAdvice
public class ExcelImportExceptionAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidFilterException(InvalidFilterException ife) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ife.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<ErrorResponse> handleStatusException(InvalidStatusException ise) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.EXPECTATION_FAILED.value(),
                ise.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<ErrorResponse> handleExceptionImportException(ExcelImportException eie) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.EXPECTATION_FAILED.value(),
                eie.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception exc) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ApiConstants.INTERNAL_SERVER_ERROR,
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


