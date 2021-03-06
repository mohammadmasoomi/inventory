package com.github.mohammadmasoomi.inventory.stock.service.exception;

import com.github.mohammadmasoomi.inventory.core.exception.GeneralException;

import java.io.Serial;

public class StockDoesNotExistException extends GeneralException {

    @Serial
    private static final long serialVersionUID = 7995166583486200625L;

    public StockDoesNotExistException() {
        super(StockErrorMessage.STOCK_DOES_NOT_EXIST_BY_ID);
    }
}
