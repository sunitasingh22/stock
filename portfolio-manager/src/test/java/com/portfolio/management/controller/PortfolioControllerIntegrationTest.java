package com.portfolio.management.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.model.StocksBO;
import com.portfolio.management.model.UserBO;
import com.portfolio.management.service.PortfolioService;

@SpringBootTest
@AutoConfigureMockMvc
public class PortfolioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PortfolioService portfolioService;

    @InjectMocks
    private PortfolioController portfolioController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetPortfolioByUserId() throws Exception {

        Long userId = 1L;
        
        // Create UserBO
        UserBO user1 = new UserBO();
        user1.setId(1L);
        user1.setUsername("sai");
        user1.setPassword("123123");
        user1.setEmail("sai@gmail.com");
        user1.setCreatedDate(null);

        // Create StocksBO
        StocksBO stock1 = new StocksBO();
        stock1.setId(1L);
        stock1.setSymbol("AAPL");
        stock1.setName("Apple Inc.");
        stock1.setStockAddedDate(null);

        StocksBO stock2 = new StocksBO();
        stock2.setId(2L);
        stock2.setSymbol("AA");
        stock2.setName("AAAAA");
        stock2.setStockAddedDate(null);

        // Create PortfolioBOs
        PortfolioBO portfolio1 = new PortfolioBO();
        portfolio1.setUser(user1);
        portfolio1.setStock(stock1);
        portfolio1.setQuantity(10);
        portfolio1.setAddedAt(null);

        PortfolioBO portfolio2 = new PortfolioBO();
        portfolio2.setUser(user1);
        portfolio2.setStock(stock2);
        portfolio2.setQuantity(20);
        portfolio2.setAddedAt(null);

        List<PortfolioBO> portfolios = Arrays.asList(portfolio1, portfolio2);

        // Mock the service call
        when(portfolioService.getPortfolioByUserId(userId)).thenReturn(portfolios);
        System.out.println(objectMapper.writeValueAsString(portfolios));

        // Act & Assert
        mockMvc.perform(get("/portfolio/{userId}", userId) // Ensure correct endpoint
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(portfolios)));
    }
}
