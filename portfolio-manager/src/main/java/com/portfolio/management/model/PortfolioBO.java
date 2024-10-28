package com.portfolio.management.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "portfolios")
@Data
public class PortfolioBO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserBO user;

	@ManyToOne
	@JoinColumn(name = "stock_id", nullable = false)
	private StocksBO stock;

	private Integer quantity;

	@Column(name = "added_at")
	private Date addedAt;

}
