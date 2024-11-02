package com.portfolio.management.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.management.dto.InsertStockRequest;
import com.portfolio.management.model.StockListBO;
import com.portfolio.management.service.PortfolioService;
import com.portfolio.management.service.StockListService;

@SpringBootTest
@AutoConfigureMockMvc
public class StockControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockListService stockService;

    @MockBean
    private PortfolioService portfolioService;
    
    @InjectMocks
    private StockController stockController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddStockRequest() throws Exception {
    	Long userId = 1L;
    	Long stockId = 1L;
        InsertStockRequest addStockRequest = new InsertStockRequest();
        addStockRequest.setQuantity(10);
        mockMvc.perform(post("/stocks/{userId}/{stockId}", userId, stockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addStockRequest)))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void testRemoveStock() throws Exception {
        Long userId = 1L;
        Long stockId = 1L;
        Integer quantity = 5;

        mockMvc.perform(put("/stocks/{userId}/{stockId}/{quantity}", userId, stockId, quantity))
                .andExpect(status().isNoContent());
    }
    

    @Test
    void testGetAllStocksByUserId() throws Exception {
        Long userId = 1L;
        List<StockListBO> stockListBO = new ArrayList<>();
        stockListBO.add(new StockListBO()); 
        when(stockService.getAllStocksByUserId(userId)).thenReturn(stockListBO);

        mockMvc.perform(get("/stocks/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }
}
