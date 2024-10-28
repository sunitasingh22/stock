/*
 * package com.portfolio.management.model;
 * 
 * import java.time.LocalDateTime;
 * 
 * import jakarta.persistence.Column; import jakarta.persistence.Entity; import
 * jakarta.persistence.FetchType; import jakarta.persistence.GeneratedValue;
 * import jakarta.persistence.GenerationType; import jakarta.persistence.Id;
 * import jakarta.persistence.JoinColumn; import jakarta.persistence.ManyToOne;
 * import jakarta.persistence.Table; import lombok.Data;
 * 
 * @Entity
 * 
 * @Table(name = "transactions")
 * 
 * @Data public class StockTransactions {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
 * 
 * @ManyToOne(fetch = FetchType.LAZY)
 * 
 * @JoinColumn(name = "user_id", nullable = false) private User user;
 * 
 * @ManyToOne(fetch = FetchType.LAZY)
 * 
 * @JoinColumn(name = "stock_id", nullable = false) private Stocks stock;
 * 
 * private Integer quantity;
 * 
 * @Column(name = "transaction_type", nullable = false)
 * //@Enumerated(EnumType.STRING) private String transactionType;
 * 
 * @Column(name = "transaction_price") private Integer transactionPrice;
 * 
 * @Column(name = "transaction_date") private LocalDateTime transactionDate;
 * 
 * }
 */