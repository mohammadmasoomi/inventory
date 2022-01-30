package com.github.mohammadmasoomi.inventory.exception;

import com.github.mohammadmasoomi.inventory.core.exception.ErrorMessage;
import com.github.mohammadmasoomi.inventory.core.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class InventoryJWTException extends GeneralException {

    public InventoryJWTException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public InventoryJWTException(String message, String code, HttpStatus httpCode) {
        super(message, code, httpCode);
    }
}
