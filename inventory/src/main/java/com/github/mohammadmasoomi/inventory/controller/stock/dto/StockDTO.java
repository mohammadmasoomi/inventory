package com.github.mohammadmasoomi.inventory.controller.stock.dto;

import com.github.mohammadmasoomi.inventory.core.validation.constraints.Amount;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class StockDTO extends RepresentationModel<StockDTO> {

    private long id;

    @NotEmpty
    @Length(min = 3, max = 100)
    private String name;

    @NotNull
    @Amount
    private BigDecimal currentPrice;

    private Date lastUpdate;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
