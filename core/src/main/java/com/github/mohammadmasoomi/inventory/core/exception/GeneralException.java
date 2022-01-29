package com.github.mohammadmasoomi.inventory.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Root of all exception, contains a code for
 * describe an error message with (code, httpCode,message) format.
 */
public class GeneralException extends RuntimeException {

    private final String code;
    private final HttpStatus httpCode;

    public GeneralException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.code = errorMessage.getCode();
        this.httpCode = errorMessage.getHttpCode();
    }

    public GeneralException(String message, String code, HttpStatus httpCode) {
        super(message);
        this.code = code;
        this.httpCode = httpCode;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpCode() {
        return httpCode;
    }
}