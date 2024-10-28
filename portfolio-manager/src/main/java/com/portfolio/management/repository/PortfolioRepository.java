package com.portfolio.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.management.model.PortfolioBO;
import com.portfolio.management.model.StocksBO;
import com.portfolio.management.model.UserBO;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioBO, Long> {

	PortfolioBO findByUserAndStock(UserBO user, StocksBO stock);

	void deleteByUserIdAndStockId(Long userId, Long stockId);

	List<PortfolioBO> findByUser(UserBO user);
	
	List<PortfolioBO> findByUserId(Long userId);
	
	PortfolioBO findByUserIdAndStockId(Long userId, Long stockId);
	

}
