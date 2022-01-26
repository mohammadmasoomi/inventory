package com.github.mohammadmasoomi.inventory.stock;

import com.github.mohammadmasoomi.inventory.InventoryApplication;
import com.github.mohammadmasoomi.inventory.stock.entity.Stock;
import com.github.mohammadmasoomi.inventory.stock.service.StockService;
import com.github.mohammadmasoomi.inventory.stock.service.exception.StockDoesNotExistException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApplication.class)
public class GetByIdTest {

    @Autowired
    private StockService stockService;

    @Test
    public void getByName() throws StockDoesNotExistException {
        Stock result = stockService.getById(SaveStockTest.STOCK_ID);
        assertEquals(SaveStockTest.STOCK_NAME, result.getName());
    }

}