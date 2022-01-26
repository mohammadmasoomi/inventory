package com.github.mohammadmasoomi.inventory.stock;

import com.github.mohammadmasoomi.inventory.InventoryApplication;
import com.github.mohammadmasoomi.inventory.stock.entity.Stock;
import com.github.mohammadmasoomi.inventory.stock.service.StockService;
import com.github.mohammadmasoomi.inventory.stock.service.exception.StockAlreadyExistException;
import com.github.mohammadmasoomi.inventory.stock.service.exception.StockDoesNotExistException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApplication.class)
public class SaveStockTest {

    public final static String STOCK_NAME = "TestStock";
    public static long STOCK_ID;

    @Autowired
    private StockService stockService;

    @Test
    public void saveStock() throws StockAlreadyExistException, StockDoesNotExistException {
        Stock stock = new Stock();
        stock.setName(STOCK_NAME);
        stock.setCurrentPrice(BigDecimal.valueOf(100.25));
        stockService.save(stock);
        STOCK_ID = stock.getId();

        Stock result = stockService.getById(stock.getId());
        assertEquals(stock.getName(), result.getName());

    }

}