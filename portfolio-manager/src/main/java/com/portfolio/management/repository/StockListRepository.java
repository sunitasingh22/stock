package com.portfolio.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.management.model.StockListBO;

public interface StockListRepository extends JpaRepository<StockListBO, Long>{

}
