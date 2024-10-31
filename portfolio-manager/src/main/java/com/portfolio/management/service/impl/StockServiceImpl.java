package com.portfolio.management.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.management.dto.InsertStockRequest;
import com.portfolio.management.exception.ResourceNotFoundException;
import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.model.StocksBO;
import com.portfolio.management.model.UserBO;
import com.portfolio.management.repository.PortfolioRepository;
import com.portfolio.management.repository.StockRepository;
import com.portfolio.management.repository.UserRepository;
import com.portfolio.management.service.StockService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StockServiceImpl implements StockService {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private UserRepository userRepository;

	public StockServiceImpl(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	public StocksBO addStock(Long userId, InsertStockRequest addStockRequest) {
		log.info("Adding new stock details for userId: {}, stock symbol: {}", userId, addStockRequest.getSymbol());

		// Find or create the stock in the database
		StocksBO stock = stockRepository.findBySymbol(addStockRequest.getSymbol()).orElseGet(() -> {
			StocksBO newStock = new StocksBO();
			newStock.setSymbol(addStockRequest.getSymbol());
			newStock.setName(addStockRequest.getName());
			return stockRepository.save(newStock);
		});

		// Fetch the user
		UserBO user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		// Check if the stock is already in the user's portfolio
		PortfolioBO existingPortfolioEntry = portfolioRepository.findByUserIdAndStockId(user.getId(), stock.getId());

		if (existingPortfolioEntry != null) {
			// Stock already exists in the portfolio, update the quantity
			log.info("Stock already in portfolio for this userId, only updating quantity", userId);
			existingPortfolioEntry.setUser(user);
			existingPortfolioEntry.setStock(stock);
			existingPortfolioEntry.setQuantity(existingPortfolioEntry.getQuantity() + addStockRequest.getQuantity());
			portfolioRepository.save(existingPortfolioEntry);
		} else {
			// Stock doesn't exist in the portfolio, create a new entry
			 log.info("This Stock is newly adding in portfolio for this userId: {}, adding new portfolio entry", userId);
			PortfolioBO newPortfolioEntry = new PortfolioBO();
			newPortfolioEntry.setUser(user);
			newPortfolioEntry.setStock(stock);
			newPortfolioEntry.setQuantity(addStockRequest.getQuantity());
			portfolioRepository.save(newPortfolioEntry);
		}

		return stock;

	}

	@Transactional
	public void deleteStock(Long stockId, Long userId) {
		log.info("Selling/Removing stock with stockId: {} for userId: {}", stockId, userId);
		UserBO user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		// Retrieve the stock
		StocksBO stock = stockRepository.findById(stockId)
				.orElseThrow(() -> new ResourceNotFoundException("Stock not found"));

		stockRepository.deleteById(stockId);
		 log.info("Stock deleted successfully", stockId);

	}

	//public List<StocksBO> getAllStocks() {
		// log.info("Fetching all stocks");
		//return stockRepository.findAll();
	//}

	public List<StocksBO> getAllStocksByUserId(Long userId) {
		log.info("Getting all stocks for userId: {}", userId);
		// Fetch the user to ensure they exist
		UserBO user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		// Fetch portfolio entries for the user
		List<PortfolioBO> portfolios = portfolioRepository.findByUserId(userId);
		if (portfolios == null) {
			log.warn("Portfolio not found for userId: {}", userId);
			return Collections.emptyList();
		}

		// Extract stocks associated with the portfolio and return the list
		return portfolios.stream().map(PortfolioBO::getStock).collect(Collectors.toList());
	}

	public String getStockSymbol(Long stockId) {
		// Fetch the stock entity based on stockId
		log.info("Getting stock symbol for given stockId: {}", stockId);
		StocksBO stock = stockRepository.findById(stockId).orElse(null);
		if (stock != null) {
			log.info("Stock symbol found: {}", stock.getSymbol());
			return stock.getSymbol();
		} else {
			 log.error("Stock symbol not found for given stockId: {}", stockId);
			throw new RuntimeException("Stock not found for given stockId: " + stockId);
		}
	}

}
