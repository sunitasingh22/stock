package com.portfolio.management.controller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.repository.PortfolioRepository;
import com.portfolio.management.service.PortfolioService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Transactional
	public void removeStockFromPortfolio(Long userId, Long stockId) {
		// This will delete the entry in the portfolio table for the given userId and
		// stockId
		log.info("Removing stock from portfolio of userId: {}", stockId, userId);
		portfolioRepository.deleteByUserIdAndStockId(userId, stockId);
		log.info("Stock removed successfully from userId: {} portfolio", stockId, userId);
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

}
