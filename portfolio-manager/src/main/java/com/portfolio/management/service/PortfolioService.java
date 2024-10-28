package com.portfolio.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.repository.PortfolioRepository;


public interface PortfolioService {


	@Transactional
	public void removeStockFromPortfolio(Long userId, Long stockId);
	
	public List<PortfolioBO> getPortfolioByUserId(Long userId);

	public PortfolioBO getPortfolio(Long userId, Long stockId);
}
