package com.portfolio.management.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.management.dto.InsertStockRequest;
import com.portfolio.management.dto.Stocks;
import com.portfolio.management.mapper.StockMapper;
import com.portfolio.management.model.StocksBO;
import com.portfolio.management.service.PortfolioService;
import com.portfolio.management.service.StockService;

@SpringBootTest
@AutoConfigureMockMvc
public class StockControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    @MockBean
    private PortfolioService portfolioService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddStock() throws Exception {
    	Long userId = 1L;
        InsertStockRequest addStockRequest = new InsertStockRequest();
        addStockRequest.setSymbol("AAPL");
        addStockRequest.setName("Apple Inc.");
        addStockRequest.setQuantity(10);

        // Mock the service call to do nothing (return null) for this non-void method
        when(stockService.addStock(userId, addStockRequest)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(post("/stocks/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addStockRequest)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteStock() throws Exception {
        Long userId = 1L;
        Long stockId = 1L;

        // Mock the service call
        doNothing().when(portfolioService).removeStockFromPortfolio(userId, stockId);
        doNothing().when(stockService).deleteStock(stockId, userId);

        // Act & Assert
        mockMvc.perform(delete("/stocks/{userId}/{stockId}", userId, stockId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllStocksByUserId() throws Exception {
        Long userId = 1L;

        // Sample stock data
        Stocks stock1 = new Stocks();
        stock1.setId(1);
        stock1.setSymbol("AAPL");
        stock1.setName("Apple Inc.");

        Stocks stock2 = new Stocks();
        stock2.setId(2);
        stock2.setSymbol("AA");
        stock2.setName("Alcoa Corp");

        List<Stocks> stocks = Arrays.asList(stock1, stock2);
        
        List<StocksBO> stockBOList = StockMapper.INSTANCE.toBOList(stocks);

        // Mock the service call
        when(stockService.getAllStocksByUserId(userId)).thenReturn(stockBOList);

        // Act & Assert
        mockMvc.perform(get("/stocks/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(stocks)));
    }
}
