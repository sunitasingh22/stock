package com.portfolio.management.controller.service.impl;

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

@Service
public class StockServiceImpl implements StockService{
	
	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private UserRepository userRepository;

	/*
	 * @Autowired private TransactionRepository transactionRepository;
	 */

	public StockServiceImpl(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	public StocksBO addStock(Long userId, InsertStockRequest addStockRequest) {

		// Step 1: Find or create the stock in the database
		StocksBO stock = stockRepository.findBySymbol(addStockRequest.getSymbol()).orElseGet(() -> {
			StocksBO newStock = new StocksBO();
			newStock.setSymbol(addStockRequest.getSymbol());
			newStock.setName(addStockRequest.getName());
			return stockRepository.save(newStock);
		});

		// Step 2: Fetch the user
		UserBO user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

		// Step 3: Check if the stock is already in the user's portfolio
		PortfolioBO existingPortfolioEntry = portfolioRepository.findByUserAndStock(user, stock);

		if (existingPortfolioEntry != null) {
			// Stock already exists in the portfolio, update the quantity
			existingPortfolioEntry.setUser(user);
			existingPortfolioEntry.setStock(stock);
			existingPortfolioEntry.setQuantity(existingPortfolioEntry.getQuantity() + addStockRequest.getQuantity());
			portfolioRepository.save(existingPortfolioEntry);
		} else {
			// Stock doesn't exist in the portfolio, create a new entry
			PortfolioBO newPortfolioEntry = new PortfolioBO();
			newPortfolioEntry.setUser(user);
			newPortfolioEntry.setStock(stock);
			newPortfolioEntry.setQuantity(addStockRequest.getQuantity());
			portfolioRepository.save(newPortfolioEntry);
		}

		/*
		 * StockTransactions stockTransactions = new StockTransactions();
		 * stockTransactions.setUser(user); stockTransactions.setStock(stock);
		 * stockTransactions.setQuantity(addStockRequest.getQuantity());
		 * stockTransactions.setTransactionType("BUY");
		 * stockTransactions.setTransactionPrice(1500); // Assuming you have the price
		 * to log stockTransactions.setTransactionDate(LocalDateTime.now());
		 * 
		 * transactionRepository.save(stockTransactions); // Save transaction record
		 */
		return stock;

	}

	@Transactional
	public void deleteStock(Long stockId, Long userId) {

		UserBO user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

		// Retrieve the stock
		StocksBO stock = stockRepository.findById(stockId)
				.orElseThrow(() -> new ResourceNotFoundException("Stock not found"));

		/*
		 * StockTransactions transactions = new StockTransactions();
		 * transactions.setUser(user); transactions.setStock(stock);
		 * transactions.setQuantity(0); // Set to 0, as it's a deletion
		 * transactions.setTransactionType("SELL"); // Treat deletion as a sell action
		 * transactions.setTransactionPrice(0); // Can be adjusted based on your needs
		 * transactions.setTransactionDate(LocalDateTime.now());
		 * 
		 * transactionRepository.save(transactions); // Save transaction record
		 */
		stockRepository.deleteById(stockId);

	}

	public List<StocksBO> getAllStocks() {
		return stockRepository.findAll();
	}

	public List<StocksBO> getAllStocksByUserId(Long userId) {
		// Fetch the user to ensure they exist
		UserBO user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
		
		  // Fetch portfolio entries for the user
        List<PortfolioBO> portfolios = portfolioRepository.findByUser(user);

        // Extract stocks associated with the portfolio and return the list
        return portfolios.stream()
                .map(PortfolioBO::getStock)  // Get the stock from each portfolio entry
                .collect(Collectors.toList());
	}

	public List<StocksBO> getStockInfoByUserId(Long userId, Long stockId) {
		UserBO user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
		
		PortfolioBO portfolio = portfolioRepository.findById(stockId)
				.orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + userId));
		
		return null;
	}
	
	public String getStockSymbol(Long stockId) {
        // Fetch the stock entity based on stockId
        StocksBO stock = stockRepository.findById(stockId).orElse(null);
        if (stock != null) {
            return stock.getSymbol(); // Assuming Stock has a getSymbol() method
        } else {
            throw new RuntimeException("Stock not found with ID: " + stockId);
        }
    }

}
