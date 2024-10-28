package com.portfolio.management.controller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.repository.PortfolioRepository;
import com.portfolio.management.service.PortfolioService;

@Service
public class PortfolioServiceImpl implements PortfolioService {
	
	@Autowired
	private PortfolioRepository portfolioRepository;

	@Transactional
	public void removeStockFromPortfolio(Long userId, Long stockId) {
		// This will delete the entry in the portfolio table for the given userId and stockId
		portfolioRepository.deleteByUserIdAndStockId(userId, stockId);
	}
	
	public List<PortfolioBO> getPortfolioByUserId(Long userId) {
        return portfolioRepository.findByUserId(userId);
    }

	public PortfolioBO getPortfolio(Long userId, Long stockId) {
        return portfolioRepository.findByUserIdAndStockId(userId, stockId);
    }

}
