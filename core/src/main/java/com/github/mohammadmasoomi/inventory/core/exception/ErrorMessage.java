package com.github.mohammadmasoomi.inventory.core.exception;

import org.springframework.http.HttpStatus;

/**
 * An abstraction of error message with (code, httpCode,message) format
 */
public interface ErrorMessage {

    String getCode();

    HttpStatus getHttpCode();

    String getMessage();

}