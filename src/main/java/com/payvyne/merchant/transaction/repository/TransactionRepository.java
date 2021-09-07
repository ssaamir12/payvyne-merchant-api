package com.payvyne.merchant.transaction.repository;

import com.payvyne.merchant.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.payvyne.merchant.transaction.entity.Transaction;

import java.math.BigDecimal;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Long>{

     Iterable<Transaction> findByStatus(String transactionStatus);

     Iterable<Transaction> findByDescription(String transactionDescription);

     Iterable<Transaction> findByCurrency(String transactionCurrency);

     Iterable<Transaction> findByAmount(BigDecimal transactionAmount);

     Iterable<Transaction> findByCurrencyAndAmount(String currency, BigDecimal amount);
}
