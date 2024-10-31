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
import com.portfolio.management.dto.Portfolio;
import com.portfolio.management.dto.Stocks;
import com.portfolio.management.dto.Users;
import com.portfolio.management.mapper.PortfolioMapper;
import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.model.StocksBO;
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
        
        Users user1 = new Users();
        user1.setId(1);
        user1.setUsername("sai");
        user1.setPassword("123123");
        user1.setEmail("sai@gmail.com");

        Stocks stock1 = new Stocks();
        stock1.setId(1);
        stock1.setSymbol("AAPL");
        stock1.setName("Apple Inc.");

        Stocks stock2 = new Stocks();
        stock2.setId(2);
        stock2.setSymbol("AA");
        stock2.setName("AAAAA");

        Portfolio portfolio1 = new Portfolio();
        portfolio1.setId(1);
        portfolio1.setUser(user1);
        portfolio1.setStock(stock1);
        portfolio1.setQuantity(10);

        Portfolio portfolio2 = new Portfolio();
        portfolio2.setId(1);
        portfolio2.setUser(user1);
        portfolio2.setStock(stock2);
        portfolio2.setQuantity(20);

        List<Portfolio> portfolioList = Arrays.asList(portfolio1, portfolio2);
        List<PortfolioBO> portfolioBOList = PortfolioMapper.INSTANCE.toBOList(portfolioList);

        when(portfolioService.getPortfolioByUserId(userId)).thenReturn(portfolioBOList);
        System.out.println(objectMapper.writeValueAsString(portfolioBOList));

        mockMvc.perform(get("/portfolio/{userId}", userId) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(portfolioList)));
    }
}
