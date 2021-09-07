package com.payvyne.merchant.transaction.service;

import com.payvyne.merchant.transaction.constants.TransactionConstants;
import com.payvyne.merchant.transaction.entity.Transaction;
import com.payvyne.merchant.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        super();
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactions() {
        return StreamSupport.stream(transactionRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    public Optional<Transaction> getTransactionByID(Long id) {
        return transactionRepository.findById(id);
    }

    @ResponseBody()
    public void createTransaction(Transaction transaction) {

        if( (transaction.getStatus() !=null
                &&  transaction.getStatus().length()>0
                && (Objects.equals( transaction.getStatus(), TransactionConstants.COMPLETED) || Objects.equals( transaction.getStatus(),TransactionConstants.FAILED)))
        &&
                ( transaction.getCurrency() !=null
                        && transaction.getCurrency().length()>0
                        && (Objects.equals(transaction.getCurrency(), TransactionConstants.EURO) || Objects.equals(transaction.getCurrency(),TransactionConstants.GBP) || Objects.equals(transaction.getCurrency(),TransactionConstants.USD))))
        {
            transactionRepository.save(transaction);
        }
        else throw new IllegalStateException("Invalid status or currency!");

    }

    @ResponseStatus(HttpStatus.OK)
    public void deleteTransaction(Long id) {

        Boolean transactionExists = transactionRepository.existsById(id);

        if(!transactionExists){
            throw new IllegalStateException("Transaction with given Id does not exist"); }

        transactionRepository.deleteById(id);
    }


    @Transactional
    public void updateTransaction(Long id, Transaction updatedTransaction) {

        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new IllegalStateException("Transaction with given Id does not exist"));

        if( updatedTransaction.getStatus() !=null
                &&  updatedTransaction.getStatus().length()>0
                && !Objects.equals( updatedTransaction.getStatus(), transaction.getStatus())
                && (Objects.equals( updatedTransaction.getStatus(), TransactionConstants.COMPLETED) || Objects.equals( updatedTransaction.getStatus(),TransactionConstants.FAILED)))
        {
            transaction.setStatus( updatedTransaction.getStatus());
            transaction.setTimestamp(LocalDateTime.now()); // Updating transaction timestamp on update
        }

        if( updatedTransaction.getAmount() !=null
                && updatedTransaction.getAmount().compareTo(BigDecimal.ZERO)>0
                && updatedTransaction.getAmount().compareTo(transaction.getAmount()) != 0)
        {
            transaction.setAmount(updatedTransaction.getAmount());
            transaction.setTimestamp(LocalDateTime.now()); // Updating transaction timestamp on update
        }

        if( updatedTransaction.getCurrency() !=null
                && updatedTransaction.getCurrency().length()>0
                && !Objects.equals(updatedTransaction.getCurrency(), transaction.getCurrency())
                && (Objects.equals(updatedTransaction.getCurrency(), TransactionConstants.EURO) || Objects.equals(updatedTransaction.getCurrency(),TransactionConstants.GBP) || Objects.equals(updatedTransaction.getCurrency(),TransactionConstants.USD)))
        {
            transaction.setCurrency(updatedTransaction.getCurrency());
            transaction.setTimestamp(LocalDateTime.now()); // Updating transaction timestamp on update
        }

        if( updatedTransaction.getDescription() !=null
                && updatedTransaction.getDescription().length()>0
                && !Objects.equals(updatedTransaction.getDescription(), transaction.getDescription()))
        {
            transaction.setDescription(updatedTransaction.getDescription());
            transaction.setTimestamp(LocalDateTime.now()); // Updating transaction timestamp on update
        }

        transactionRepository.save(transaction);
    }

    public Iterable<Transaction> getTransactionByStatus(String transactionStatus) {
        if( transactionStatus !=null
                &&  transactionStatus.length()>0
                && (Objects.equals( transactionStatus, TransactionConstants.COMPLETED) || Objects.equals( transactionStatus,TransactionConstants.FAILED))) {
            return transactionRepository.findByStatus(transactionStatus);
        }
        else  throw new IllegalStateException("Invalid status!");
    }

    public Iterable<Transaction> getTransactionByDescription(String transactionDescription) {
        if( transactionDescription !=null
                &&  transactionDescription.length()>0){
            return transactionRepository.findByDescription(transactionDescription);
        }
        else  throw new IllegalStateException("Invalid input!");
    }

    public Iterable<Transaction> getTransactionByCurrency(String transactionCurrency) {
        if( transactionCurrency !=null
                &&  transactionCurrency.length()>0
                && (Objects.equals(transactionCurrency, TransactionConstants.EURO) || Objects.equals(transactionCurrency,TransactionConstants.GBP) || Objects.equals(transactionCurrency,TransactionConstants.USD))){
            return transactionRepository.findByCurrency(transactionCurrency);
        }
        else  throw new IllegalStateException("Invalid currency!");
    }
    public Iterable<Transaction> getTransactionByAmount(BigDecimal transactionAmount) {
        if( transactionAmount !=null
                && transactionAmount.compareTo(BigDecimal.ZERO)>0) {
            return transactionRepository.findByAmount(transactionAmount);
        }
        else  throw new IllegalStateException("Invalid amount!");
    }

    public Iterable<Transaction> getTransactionBasedOnCurrencyandAmount(String currency, BigDecimal amount) {
        System.out.println("Values inside Service are  "+currency+" and "+amount );
        if( amount !=null
                && amount.compareTo(BigDecimal.ZERO)>0 && currency !=null
                &&  currency.length()>0
                && (Objects.equals(currency, TransactionConstants.EURO) || Objects.equals(currency,TransactionConstants.GBP) || Objects.equals(currency,TransactionConstants.USD))){

            System.out.println();
            return transactionRepository.findByCurrencyAndAmount(currency,amount);
        }
        else  throw new IllegalStateException("Invalid currency or incorrect amount!");
    }

}
