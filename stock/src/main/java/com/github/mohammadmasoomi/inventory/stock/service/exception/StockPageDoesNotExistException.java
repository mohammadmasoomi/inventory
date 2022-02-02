package com.github.mohammadmasoomi.inventory.stock.service.exception;

import com.github.mohammadmasoomi.inventory.core.exception.GeneralException;

import java.io.Serial;

public class StockPageDoesNotExistException extends GeneralException {

    @Serial
    private static final long serialVersionUID = 6428642563708253332L;

    public StockPageDoesNotExistException() {
        super(StockErrorMessage.STOCK_PAGE_DOES_NOT_EXIST);
    }
}
