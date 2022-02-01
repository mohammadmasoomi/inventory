package com.github.mohammadmasoomi.inventory.configuration.mapper;

import com.github.mohammadmasoomi.inventory.controller.stock.dto.StockDTO;
import com.github.mohammadmasoomi.inventory.stock.entity.Stock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper {

    Stock destinationToSource(StockDTO destination);

    StockDTO sourceToDestination(Stock source);

}

