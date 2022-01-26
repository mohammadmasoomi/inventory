package com.github.mohammadmasoomi.inventory.stock;

import com.github.mohammadmasoomi.inventory.InventoryApplication;
import com.github.mohammadmasoomi.inventory.stock.service.StockService;
import com.github.mohammadmasoomi.inventory.stock.service.exception.StockDoesNotExistException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApplication.class)
public class DeleteByIdTest {

    @Autowired
    private StockService stockService;

    @Test(expected = StockDoesNotExistException.class)
    public void deleteByName() throws StockDoesNotExistException {
        stockService.deleteById(SaveStockTest.STOCK_ID);
        stockService.getById(SaveStockTest.STOCK_ID);
    }

}