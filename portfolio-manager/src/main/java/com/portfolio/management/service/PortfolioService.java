package com.portfolio.management.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.portfolio.management.dto.InsertStockRequest;
import com.portfolio.management.model.PortfolioBO;

public interface PortfolioService {

	@Transactional
	public void removeStockFromPortfolio(Long userId, Long stockId, Integer quantity);

	public List<PortfolioBO> getPortfolioByUserId(Long userId);

	public PortfolioBO getPortfolioByUserIdAndStockId(Long userId, Long stockId);

	public void addStockRequest(Long userId, Long stockId, InsertStockRequest addStockRequest);
}
