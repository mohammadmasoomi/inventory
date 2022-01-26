package com.github.mohammadmasoomi.inventory.stock.service.exception;

import com.github.mohammadmasoomi.inventory.core.exception.ErrorMessage;
import org.springframework.http.HttpStatus;

public enum StockErrorMessage implements ErrorMessage {
    STOCK_ALREADY_EXIST_BY_NAME("100", HttpStatus.CONFLICT, "Stock with this name already exist"),
    STOCK_DOES_NOT_EXIST_BY_ID("101", HttpStatus.NOT_FOUND, "Stock with this id does not exist"),
    STOCK_PAGE_DOES_NOT_EXIST("102", HttpStatus.NOT_FOUND, "Stock page does not exist");

    private final String code;
    private final String message;
    private final HttpStatus httpCode;

    StockErrorMessage(String code, HttpStatus httpCode, String message) {
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