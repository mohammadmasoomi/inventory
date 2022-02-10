package com.github.mohammadmasoomi.inventory.stock.service.exception;

import com.github.mohammadmasoomi.inventory.core.exception.GeneralException;

import java.io.Serial;

public class StockNotFoundByCriteriaException extends GeneralException {

    @Serial
    private static final long serialVersionUID = 6696501999314670991L;

    public StockNotFoundByCriteriaException() {
        super(StockErrorMessage.STOCK_DOES_NOT_EXIST_BY_ID);
    }
}
