package com.github.mohammadmasoomi.inventory.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.mohammadmasoomi.inventory.configuration.mapper.StockMapper;
import com.github.mohammadmasoomi.inventory.configuration.mapper.StockMapperImpl;
import com.github.mohammadmasoomi.inventory.controller.stock.StockController;
import com.github.mohammadmasoomi.inventory.controller.stock.dto.StockDTO;
import com.github.mohammadmasoomi.inventory.stock.entity.Stock;
import com.github.mohammadmasoomi.inventory.stock.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class StockControllerSuccessTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter objectWriter = objectMapper.writer();
    Stock RECORD_1 = new Stock("STOCK1", BigDecimal.valueOf(100L));
    Stock RECORD_2 = new Stock("STOCK2", BigDecimal.valueOf(200L));
    Stock RECORD_3 = new Stock("STOCK3", BigDecimal.valueOf(300L));
    Stock RECORD_4 = new Stock("STOCK4", BigDecimal.valueOf(100.10));
    StockDTO STOCK_DTO_1 = new StockDTO("STOCK_DTO1", BigDecimal.valueOf(100.10));
    private MockMvc mockMvc;
    @Mock
    private StockService stockService;
    @Spy
    private StockMapper stockMapper = new StockMapperImpl();
    @InjectMocks
    private StockController stockController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
    }

    @Test
    public void getAllSuccess() throws Exception {
        List<Stock> stocks = Arrays.asList(RECORD_1, RECORD_2, RECORD_3);
        Mockito.when(stockService.getAll(1)).thenReturn(stocks);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/stocks").param("page", "1").
                        contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3))).
                andExpect(MockMvcResultMatchers.jsonPath("$[2].name", is(RECORD_3.getName())));
    }

    @Test
    public void getByIdSuccess() throws Exception {
        int stockId = 157;
        Mockito.when(stockService.getById(stockId)).thenReturn(RECORD_4);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stocks/{id}", stockId).
                        contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$.name", is(RECORD_4.getName())));
    }

    @Test
    public void saveSuccess() throws Exception {

        Stock stock = stockMapper.destinationToSource(STOCK_DTO_1);
        Mockito.when(stockService.save(stock)).thenReturn(stock);

        String content = objectWriter.writeValueAsString(STOCK_DTO_1);

        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/api/stocks").contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.APPLICATION_JSON_VALUE).content(content);
        mockMvc.perform(postRequest).andExpect(status().isCreated());
    }

    @Test
    public void updateStockPriceSuccess() throws Exception {
        Mockito.when(stockService.updateStockPrice(157, BigDecimal.valueOf(100.10))).thenReturn(RECORD_4);

        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.patch("/api/stocks/{id}/{price}", 157L, 100.10).
                accept(MediaType.APPLICATION_JSON_VALUE));
        perform.andExpect(status().isOk());
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.currentPrice", is(100.1)));
    }

    @Test
    public void deleteSuccess() throws Exception {
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.delete("/api/stocks/{id}", 157L).
                accept(MediaType.TEXT_PLAIN_VALUE));
        perform.andExpect(status().isOk());
        perform.andExpect(MockMvcResultMatchers.content().string("Stock deleted"));
    }

}
