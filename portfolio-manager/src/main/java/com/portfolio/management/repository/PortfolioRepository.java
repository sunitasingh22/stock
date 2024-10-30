package com.portfolio.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.management.model.PortfolioBO;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioBO, Long> {
	
	PortfolioBO findByUserIdAndStockId(Long userId, Long stockId);
	
	List<PortfolioBO> findByUserId(Long userId);

	void deleteByUserIdAndStockId(Long userId, Long stockId);

}
