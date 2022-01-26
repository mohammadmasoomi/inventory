package com.github.mohammadmasoomi.inventory.stock;

import com.github.mohammadmasoomi.inventory.InventoryApplication;
import com.github.mohammadmasoomi.inventory.stock.entity.Stock;
import com.github.mohammadmasoomi.inventory.stock.service.StockService;
import com.github.mohammadmasoomi.inventory.stock.service.exception.StockPageDoesNotExistException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApplication.class)
public class GetByAllTest {

    @Autowired
    private StockService stockService;

    @Test
    public void getByAll() throws StockPageDoesNotExistException {
        List<Stock> result = stockService.getAll(1);
        assertEquals(5, result.size());
    }
}