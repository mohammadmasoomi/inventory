package com.github.mohammadmasoomi.inventory.exception;

import com.github.mohammadmasoomi.inventory.core.exception.ErrorMessage;
import org.springframework.http.HttpStatus;

public enum AppErrorMessage implements ErrorMessage {

    SERVER_INTERNAL_ERROR("200", HttpStatus.INTERNAL_SERVER_ERROR, "Server Internal Error"),
    VALIDATION_ERROR("201", HttpStatus.BAD_REQUEST, "Validation error"),
    WRONG_CREDENTIALS("202", HttpStatus.UNAUTHORIZED, "Wrong credentials"),
    USER_ALREADY_IS_DISABLED("203", HttpStatus.UNAUTHORIZED, "User already is disabled"),
    CREDENTIALS_EXPIRED("204", HttpStatus.UNAUTHORIZED, "Credentials Expired!"),
    ACCOUNT_EXPIRED("205", HttpStatus.UNAUTHORIZED, "Account Expired!"),
    TEMPORARY_ACCOUNT_LOCKED("206", HttpStatus.UNAUTHORIZED, "Account is temporarily locked"),
    ACCOUNT_DENIED("207", HttpStatus.UNAUTHORIZED, "Access denied error"),
    INSUFFICIENT_AUTHENTICATION("208", HttpStatus.UNAUTHORIZED, "Insufficient authentication"),
    UNKNOWN_AUTHENTICATION_ERROR("209", HttpStatus.UNAUTHORIZED, "Unknown authentication error");

    private final String code;
    private final String message;
    private final HttpStatus httpCode;

    AppErrorMessage(String code, HttpStatus httpCode, String message) {
        this.code = code;
        this.message = message;
        this.httpCode = httpCode;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpCode() {
        return httpCode;
    }

}