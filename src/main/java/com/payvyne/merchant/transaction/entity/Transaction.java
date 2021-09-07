package com.payvyne.merchant.transaction.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RedisHash("transaction")
public class Transaction implements Serializable{

    private static final long serialVersionUID = 3L;

    @Id
    private Long id;
    @Indexed
    private String status;
    @Indexed
    private BigDecimal amount;
    @Indexed
    private String currency;
    @Indexed
    private String description;
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public Transaction() {
    }

    public Transaction(String status, BigDecimal amount, String currency, String description, LocalDateTime timestamp) {
        this.status = status;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.timestamp = timestamp;
    }

    public Transaction(Long id, String status, BigDecimal amount, String currency, String description, LocalDateTime timestamp) {
        this.id = id;
        this.status = status;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.timestamp = timestamp;
    }
    public Transaction( String status, BigDecimal amount, String currency, String description) {
        this.status = status;
        this.amount = amount;
        this.currency = currency;
        this.description = description;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        this.timestamp = LocalDateTime.now();
        return timestamp;}

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
