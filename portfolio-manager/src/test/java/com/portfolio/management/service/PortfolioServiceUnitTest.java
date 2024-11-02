package com.portfolio.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.portfolio.management.dto.InsertStockRequest;
import com.portfolio.management.exception.ResourceNotFoundException;
import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.model.StockListBO;
import com.portfolio.management.model.UserBO;
import com.portfolio.management.repository.PortfolioRepository;
import com.portfolio.management.repository.StockListRepository;
import com.portfolio.management.repository.UserRepository;
import com.portfolio.management.service.impl.PortfolioServiceImpl;

public class PortfolioServiceUnitTest {

	@InjectMocks
    private PortfolioServiceImpl portfolioService;

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
    void testRemoveStockFromPortfolio_UserNotFound() {
        // Arrange
        Long userId = 1L;
        Long stockId = 1L;
        Integer quantity = 10;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            portfolioService.removeStockFromPortfolio(userId, stockId, quantity);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testRemoveStockFromPortfolio_StockNotFound() {
        // Arrange
        Long userId = 1L;
        Long stockId = 1L;
        Integer quantity = 10;

        UserBO user = new UserBO();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(stockListRepository.findById(stockId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            portfolioService.removeStockFromPortfolio(userId, stockId, quantity);
        });

        assertEquals("Stock not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(stockListRepository, times(1)).findById(stockId);
    }

    @Test
    void testRemoveStockFromPortfolio_Success() {
        // Arrange
        Long userId = 1L;
        Long stockId = 1L;
        Integer quantity = 10;

        UserBO user = new UserBO();
        StockListBO stock = new StockListBO();
        PortfolioBO existingPortfolio = new PortfolioBO();
        existingPortfolio.setQuantity(15); // Existing quantity in portfolio

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(stockListRepository.findById(stockId)).thenReturn(Optional.of(stock));
        when(portfolioRepository.findByUserIdAndStockId(userId, stockId)).thenReturn(existingPortfolio);

        // Act
        portfolioService.removeStockFromPortfolio(userId, stockId, quantity);

        // Assert
        assertEquals(5, existingPortfolio.getQuantity()); // Ensure quantity is updated correctly
        verify(portfolioRepository, times(1)).save(existingPortfolio);
    }

    @Test
    void testGetPortfolioByUserId() {
        // Arrange
        Long userId = 1L;
        PortfolioBO portfolio = new PortfolioBO();
        when(portfolioRepository.findByUserId(userId)).thenReturn(Collections.singletonList(portfolio));

        // Act
        List<PortfolioBO> result = portfolioService.getPortfolioByUserId(userId);

        // Assert
        assertEquals(1, result.size());
        assertEquals(portfolio, result.get(0));
        verify(portfolioRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetPortfolioByUserIdAndStockId_Success() {
        // Arrange
        Long userId = 1L;
        Long stockId = 1L;
        PortfolioBO portfolio = new PortfolioBO();
        when(portfolioRepository.findByUserIdAndStockId(userId, stockId)).thenReturn(portfolio);

        // Act
        PortfolioBO result = portfolioService.getPortfolioByUserIdAndStockId(userId, stockId);

        // Assert
        assertEquals(portfolio, result);
        verify(portfolioRepository, times(1)).findByUserIdAndStockId(userId, stockId);
    }

    @Test
    void testGetPortfolioByUserIdAndStockId_NotFound() {
        // Arrange
        Long userId = 1L;
        Long stockId = 1L;
        when(portfolioRepository.findByUserIdAndStockId(userId, stockId)).thenReturn(null);

        // Act
        PortfolioBO result = portfolioService.getPortfolioByUserIdAndStockId(userId, stockId);

        // Assert
        assertNull(result);
        verify(portfolioRepository, times(1)).findByUserIdAndStockId(userId, stockId);
    }

    @Test
    void testAddStockRequest_UserNotFound() {
        // Arrange
        Long userId = 1L;
        Long stockId = 1L;
        InsertStockRequest addStockRequest = new InsertStockRequest();
        addStockRequest.setQuantity(10);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            portfolioService.addStockRequest(userId, stockId, addStockRequest);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testAddStockRequest_StockNotFound() {
        // Arrange
        Long userId = 1L;
        Long stockId = 1L;
        InsertStockRequest addStockRequest = new InsertStockRequest();
        addStockRequest.setQuantity(10);

        UserBO user = new UserBO();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(stockListRepository.findById(stockId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            portfolioService.addStockRequest(userId, stockId, addStockRequest);
        });

        assertEquals("Stock not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(stockListRepository, times(1)).findById(stockId);
    }

    @Test
    void testAddStockRequest_StockAlreadyInPortfolio() {
        // Arrange
        Long userId = 1L;
        Long stockId = 1L;
        InsertStockRequest addStockRequest = new InsertStockRequest();
        addStockRequest.setQuantity(10);

        UserBO user = new UserBO();
        StockListBO stock = new StockListBO();
        PortfolioBO existingPortfolio = new PortfolioBO();
        existingPortfolio.setQuantity(5); // Existing quantity in portfolio

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(stockListRepository.findById(stockId)).thenReturn(Optional.of(stock));
        when(portfolioRepository.findByUserIdAndStockId(userId, stockId)).thenReturn(existingPortfolio);

        // Act
        portfolioService.addStockRequest(userId, stockId, addStockRequest);

        // Assert
        assertEquals(15, existingPortfolio.getQuantity()); // Ensure quantity is updated correctly
        verify(portfolioRepository, times(1)).save(existingPortfolio);
    }

    @Test
    void testAddStockRequest_NewStock() {
        // Arrange
        Long userId = 1L;
        Long stockId = 1L;
        InsertStockRequest addStockRequest = new InsertStockRequest();
        addStockRequest.setQuantity(10);

        UserBO user = new UserBO();
        StockListBO stock = new StockListBO();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(stockListRepository.findById(stockId)).thenReturn(Optional.of(stock));
        when(portfolioRepository.findByUserIdAndStockId(userId, stockId)).thenReturn(null);

        // Act
        portfolioService.addStockRequest(userId, stockId, addStockRequest);

        // Assert
        verify(portfolioRepository, times(1)).save(any(PortfolioBO.class));
    }

}
