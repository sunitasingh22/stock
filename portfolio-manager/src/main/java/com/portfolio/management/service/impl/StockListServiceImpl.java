package com.portfolio.management.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.management.exception.ResourceNotFoundException;
import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.model.StockListBO;
import com.portfolio.management.model.UserBO;
import com.portfolio.management.repository.PortfolioRepository;
import com.portfolio.management.repository.StockListRepository;
import com.portfolio.management.repository.UserRepository;
import com.portfolio.management.service.StockListService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StockListServiceImpl implements StockListService{
	
	private StockListRepository stockListRepository;
	
	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private UserRepository userRepository;

    @Autowired
    public StockListServiceImpl(StockListRepository stockListRepository) {
        this.stockListRepository = stockListRepository;
    }
	
	@Override
	public List<StockListBO> getAllStocks() {
		 return stockListRepository.findAll();
	}
	
	public List<StockListBO> getAllStocksByUserId(Long userId) {
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
	
}
