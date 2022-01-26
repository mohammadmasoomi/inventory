package com.github.mohammadmasoomi.inventory.configuration;

import com.github.mohammadmasoomi.inventory.controller.stock.dto.StockDTO;
import com.github.mohammadmasoomi.inventory.stock.entity.Stock;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Mapper
    public interface StockMapper {
        Stock destinationToSource(StockDTO destination);
    }

    @Mapper
    public interface StockDTOMapper {
        StockDTO sourceToDestination(Stock source);
    }

}
