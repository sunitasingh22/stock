package com.portfolio.management.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.management.dto.InsertStockRequest;
import com.portfolio.management.exception.ResourceNotFoundException;
import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.model.StockListBO;
import com.portfolio.management.model.UserBO;
import com.portfolio.management.repository.PortfolioRepository;
import com.portfolio.management.repository.StockListRepository;
import com.portfolio.management.repository.UserRepository;
import com.portfolio.management.service.PortfolioService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {

	@Autowired
	private StockListRepository stockListRepository;

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void removeStockFromPortfolio(Long userId, Long stockId, Integer quantity) {
		// This will delete the entry in the portfolio table for the given userId and
		// stockId
		log.info("Removing stock from portfolio of userId: {}", stockId, userId);

		// Fetch the user
		UserBO user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		StockListBO stock = stockListRepository.findById(stockId)
				.orElseThrow(() -> new ResourceNotFoundException("Stock not found"));

		// Check if the stock is already in the user's portfolio
		PortfolioBO existingPortfolioEntry = portfolioRepository.findByUserIdAndStockId(userId, stockId);

		if (existingPortfolioEntry != null) {
			// Stock already exists in the portfolio, update the quantity
			log.info("Stock already in portfolio for this userId, only updating quantity", userId);
			existingPortfolioEntry.setUser(user);
			existingPortfolioEntry.setStock(stock);
			existingPortfolioEntry.setQuantity(existingPortfolioEntry.getQuantity() - quantity);
			portfolioRepository.save(existingPortfolioEntry);
		} 

		log.info("Stock updated successfully from userId: {} portfolio", stockId, userId);
	}

	public List<PortfolioBO> getPortfolioByUserId(Long userId) {
		log.info("Getting portfolio stock details for userId: {}", userId);
		List<PortfolioBO> porfolioStockList = portfolioRepository.findByUserId(userId);
		log.info("Received portfolio stock data for userId: {}", userId);
		return porfolioStockList;
	}

	public PortfolioBO getPortfolioByUserIdAndStockId(Long userId, Long stockId) {
		log.info("Getting portfolio entry for userId: {} and stockId: {}", userId, stockId);
		PortfolioBO portfolioByUserIdAndStockId = portfolioRepository.findByUserIdAndStockId(userId, stockId);
		if (portfolioByUserIdAndStockId != null) {
			log.info("Portfolio entry found for userId: {} and stockId: {}", userId, stockId);
		} else {
			log.warn("No portfolio entry found for userId: {} and stockId: {}", userId, stockId);
		}
		return portfolioByUserIdAndStockId;
	}

	@Override
	public void addStockRequest(Long userId, Long stockId, InsertStockRequest addStockRequest) {

		// Fetch the user
		UserBO user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		StockListBO stock = stockListRepository.findById(stockId)
				.orElseThrow(() -> new ResourceNotFoundException("Stock not found"));

		// Check if the stock is already in the user's portfolio
		PortfolioBO existingPortfolioEntry = portfolioRepository.findByUserIdAndStockId(userId, stockId);

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

	}

}
