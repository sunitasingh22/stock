package com.portfolio.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.management.model.StockListBO;

@Repository
public interface StockListRepository extends JpaRepository<StockListBO, Long>{

}
