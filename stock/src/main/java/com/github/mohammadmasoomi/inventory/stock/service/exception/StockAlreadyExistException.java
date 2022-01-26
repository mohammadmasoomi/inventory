package com.github.mohammadmasoomi.inventory.stock.service.exception;

import com.github.mohammadmasoomi.inventory.core.exception.GeneralException;

public class StockAlreadyExistException extends GeneralException {

    public StockAlreadyExistException() {
        super(StockErrorMessage.STOCK_ALREADY_EXIST_BY_NAME);
    }
}
