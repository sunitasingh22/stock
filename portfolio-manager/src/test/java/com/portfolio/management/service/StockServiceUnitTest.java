package com.portfolio.management.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.portfolio.management.dto.InsertStockRequest;
import com.portfolio.management.exception.ResourceNotFoundException;
import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.model.StocksBO;
import com.portfolio.management.model.UserBO;
import com.portfolio.management.repository.PortfolioRepository;
import com.portfolio.management.repository.StockRepository;
import com.portfolio.management.repository.UserRepository;
import com.portfolio.management.service.impl.StockServiceImpl;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class StockServiceUnitTest {

	@InjectMocks
	private StockServiceImpl stockService;

	@Mock
	private StockRepository stockRepository;

	@Mock
	private PortfolioRepository portfolioRepository;

	@Mock
	private UserRepository userRepository;

	private StocksBO stock;
	private UserBO user;
	private PortfolioBO portfolio;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		assertThat(stockService).isNotNull();
		assertThat(userRepository).isNotNull();
		assertThat(stockRepository).isNotNull();
		assertThat(portfolioRepository).isNotNull();

		user = new UserBO();
		user.setId(1L);
		user.setUsername("testUser");
		user.setPassword("password");

		stock = new StocksBO();
		stock.setId(1L);
		stock.setSymbol("AAPL");

		portfolio = new PortfolioBO();
		portfolio.setUser(user);
		portfolio.setStock(stock);
		portfolio.setQuantity(11);
	}

	/*
	 * @Test public void testGetAllStocks() {
	 * when(stockRepository.findAll()).thenReturn(Arrays.asList(stock));
	 * 
	 * List<StocksBO> stocks = stockService.getAllStocks();
	 * 
	 * assertThat(stocks).isNotNull(); assertThat(stocks.size()).isEqualTo(1);
	 * assertThat(stocks.get(0).getSymbol()).isEqualTo("AAPL"); }
	 */

	@Test
	public void testGetStockSymbol() {
		when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

		String stockSymbol = stockService.getStockSymbol(1L);

		assertThat(stockSymbol).isEqualTo("AAPL");
	}

	@Test
	public void testGetStockSymbol_NotFound() {
		when(stockRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			stockService.getStockSymbol(1L);
		});
	}

	@Test
	public void testAddStock_NewStock() {
		InsertStockRequest request = new InsertStockRequest();
		request.setSymbol("AAPL");
		request.setName("Apple Inc.");
		request.setQuantity(5);

		lenient().when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		lenient().when(stockRepository.findBySymbol(anyString())).thenReturn(Optional.empty());

		when(stockRepository.save(any(StocksBO.class))).thenReturn(stock);
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(portfolioRepository.findByUserIdAndStockId(1L, 1L)).thenReturn(null);

		StocksBO result = stockService.addStock(1L, request);

		assertThat(result).isNotNull();
		assertThat(result.getSymbol()).isEqualTo("AAPL");

		verify(stockRepository, times(1)).save(any(StocksBO.class));
		verify(portfolioRepository, times(1)).save(any(PortfolioBO.class));
	}

	@Test
	public void testAddStock_ExistingStock() {
		InsertStockRequest request = new InsertStockRequest();
		request.setSymbol("AAPL");
		request.setName("Apple Inc.");
		request.setQuantity(5);

		when(stockRepository.findBySymbol("AAPL")).thenReturn(Optional.of(stock));
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(portfolioRepository.findByUserIdAndStockId(anyLong(), anyLong())).thenReturn(portfolio);

		StocksBO addedStock = stockService.addStock(1L, request);

		assertThat(addedStock).isNotNull();
		assertThat(addedStock.getSymbol()).isEqualTo("AAPL");
		assertThat(portfolio.getQuantity()).isEqualTo(16);
		verify(portfolioRepository, times(1)).save(portfolio);
	}

	@Test
	public void testDeleteStock() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

		stockService.deleteStock(1L, 1L);

		verify(stockRepository, times(1)).deleteById(1L);
	}

	@Test
	public void testGetAllStocksByUserId_UserNotFound_ShouldThrowResourceNotFoundException() {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> stockService.getAllStocksByUserId(1L));
	}

	//@Test
	/*public void testGetAllStocksByUserId() {
		given(userRepository.findById(1L)).willReturn(Optional.of(user));
		given(portfolioRepository.findByUserId(1L)).willReturn(List.of(portfolio));

		List<StocksBO> stockList = stockService.getAllStocksByUserId(1L);

		assertThat(stockList).isNotNull().hasSize(1);
		assertThat(stockList.get(0).getSymbol()).isEqualTo("AAPL");

		then(userRepository).should().findById(1L);
		then(portfolioRepository).should().findByUserId(1L);
		then(stockRepository).shouldHaveNoMoreInteractions();
	}*/

	@Test
	public void testAddStock_UserNotFound() {
		InsertStockRequest request = new InsertStockRequest();
		request.setSymbol("AAPL");
		request.setName("Apple Inc.");
		request.setQuantity(5);

		when(stockRepository.findBySymbol("AAPL")).thenReturn(Optional.empty());
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			stockService.addStock(1L, request);
		});
	}
}
