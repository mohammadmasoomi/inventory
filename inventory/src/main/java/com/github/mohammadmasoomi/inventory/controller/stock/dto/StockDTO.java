package com.github.mohammadmasoomi.inventory.controller.stock.dto;

import com.github.mohammadmasoomi.inventory.core.validation.constraints.Amount;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class StockDTO extends RepresentationModel<StockDTO> {

    private long id;

    @NotEmpty
    @Length(min = 3, max = 100)
    private String name;

    @NotNull
    @Amount
    private BigDecimal currentPrice;

    private Date lastUpdate;

    public StockDTO(String name, BigDecimal currentPrice) {
        this.name = name;
        this.currentPrice = currentPrice;
    }
}
