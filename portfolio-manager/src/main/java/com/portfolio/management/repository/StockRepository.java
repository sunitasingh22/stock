package com.portfolio.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.management.model.StocksBO;

@Repository
public interface StockRepository extends JpaRepository<StocksBO, Long> {

	Optional<StocksBO> findBySymbol(String symbol);
}
