package com.github.mohammadmasoomi.inventory.stock;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({SaveStockTest.class, GetByIdTest.class, GetByAllTest.class,
        DeleteByIdTest.class})
public class StockServiceTest {


}