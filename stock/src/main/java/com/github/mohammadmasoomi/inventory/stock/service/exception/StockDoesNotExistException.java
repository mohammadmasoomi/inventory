package com.github.mohammadmasoomi.inventory.stock.service.exception;

import com.github.mohammadmasoomi.inventory.core.exception.GeneralException;

public class StockDoesNotExistException extends GeneralException {

    public StockDoesNotExistException() {
        super(StockErrorMessage.STOCK_DOES_NOT_EXIST_BY_ID);
    }
}
