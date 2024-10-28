package com.portfolio.management.controller;

import com.portfolio.management.dto.InsertStockRequest;
import com.portfolio.management.model.StocksBO;
import com.portfolio.management.service.PortfolioService;
import com.portfolio.management.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StockControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private StockController stockController;

    @Mock
    private StockService stockService;

    @Mock
    private PortfolioService portfolioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
    }

    @Test
    void testAddStock() throws Exception {
        InsertStockRequest request = new InsertStockRequest();
        request.setName("Apple Inc.");
        request.setSymbol("AAPL");
        request.setQuantity(10);

        mockMvc.perform(post("/stocks/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"symbol\": \"AAPL\", \"quantity\": 1}"))
                .andExpect(status().isNoContent());

        verify(stockService, times(1)).addStock(eq(1L), any(InsertStockRequest.class));
    }

    @Test
    void testGetAllStocksByUserId() throws Exception {
        StocksBO stock = new StocksBO();
        stock.setId(1L);
        stock.setName("Apple Inc.");
        stock.setSymbol("AAPL");
        List<StocksBO> stocks = Collections.singletonList(stock);

        when(stockService.getAllStocksByUserId(1L)).thenReturn(stocks);

        mockMvc.perform(get("/stocks/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Apple Inc."))
                .andExpect(jsonPath("$[0].symbol").value("AAPL"));

        verify(stockService, times(1)).getAllStocksByUserId(1L);
    }

    
    
    
    
    
	/*
	 * @Test void testDeleteStock() throws Exception {
	 * mockMvc.perform(delete("/stocks/{userId}/{stockId}", 1, 24))
	 * .andExpect(status().isNoContent());
	 * 
	 * verify(portfolioService, times(1)).removeStockFromPortfolio(1L, 24L);
	 * verify(stockService, times(1)).deleteStock(2L, 1L); }
	 */
}

