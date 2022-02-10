package com.github.mohammadmasoomi.inventory.stock.repository;

import com.github.mohammadmasoomi.inventory.core.repository.base.BaseSpecification;
import com.github.mohammadmasoomi.inventory.core.repository.base.SearchCriteria;
import com.github.mohammadmasoomi.inventory.stock.entity.Stock;

import java.io.Serial;

public class StockSpecification extends BaseSpecification<Stock> {

    @Serial
    private static final long serialVersionUID = -805203245892519013L;

    public StockSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
