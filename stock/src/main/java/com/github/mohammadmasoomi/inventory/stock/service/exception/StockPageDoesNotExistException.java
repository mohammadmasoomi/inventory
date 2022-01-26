package com.github.mohammadmasoomi.inventory.stock.service.exception;

import com.github.mohammadmasoomi.inventory.core.exception.GeneralException;

public class StockPageDoesNotExistException extends GeneralException {

    public StockPageDoesNotExistException() {
        super(StockErrorMessage.STOCK_PAGE_DOES_NOT_EXIST);
    }
}
