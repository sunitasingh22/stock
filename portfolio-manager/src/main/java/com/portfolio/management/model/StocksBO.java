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
@Table(name = "stocks")
@Data
public class StocksBO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="symbol")
	private String symbol;
	
	@Column(name="name")
	private String name;
	
	@Column(name="created_at")
	private Date stockAddedDate;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "user_id") private User user;
	 */

}