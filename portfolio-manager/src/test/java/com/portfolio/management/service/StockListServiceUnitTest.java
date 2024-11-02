package com.portfolio.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.portfolio.management.exception.ResourceNotFoundException;
import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.model.StockListBO;
import com.portfolio.management.model.UserBO;
import com.portfolio.management.repository.PortfolioRepository;
import com.portfolio.management.repository.StockListRepository;
import com.portfolio.management.repository.UserRepository;
import com.portfolio.management.service.impl.StockListServiceImpl;

@ExtendWith(MockitoExtension.class)
public class StockListServiceUnitTest {

	@InjectMocks
	private StockListServiceImpl stockListService;

	@Mock
	private StockListRepository stockListRepository;

	@Mock
	private PortfolioRepository portfolioRepository;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
    void testGetAllStocks() {
        List<StockListBO> stockList = new ArrayList<>();
        stockList.add(new StockListBO());
        when(stockListRepository.findAll()).thenReturn(stockList);

        List<StockListBO> result = stockListService.getAllStocks();

        assertEquals(1, result.size());
        verify(stockListRepository, times(1)).findAll();
    }

    @Test
    void testGetAllStocksByUserIdUserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            stockListService.getAllStocksByUserId(userId);
        });

        assertEquals("User not found with id: " + userId, exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetAllStocksByUserIdPortfolioNotFound() {
        Long userId = 1L;
        UserBO user = new UserBO(); 
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(portfolioRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        List<StockListBO> result = stockListService.getAllStocksByUserId(userId);

        assertEquals(0, result.size());
        verify(userRepository, times(1)).findById(userId);
        verify(portfolioRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetAllStocksByUserIdSuccess() {
        Long userId = 1L;
        UserBO user = new UserBO();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        PortfolioBO portfolio = new PortfolioBO();
        StockListBO stock = new StockListBO();
        portfolio.setStock(stock);
        when(portfolioRepository.findByUserId(userId)).thenReturn(Collections.singletonList(portfolio));

        List<StockListBO> result = stockListService.getAllStocksByUserId(userId);

        assertEquals(1, result.size());
        assertEquals(stock, result.get(0));
        verify(userRepository, times(1)).findById(userId);
        verify(portfolioRepository, times(1)).findByUserId(userId);
    }
}
