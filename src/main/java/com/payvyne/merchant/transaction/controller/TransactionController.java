package com.payvyne.merchant.transaction.controller;

import com.payvyne.merchant.transaction.constants.TransactionConstants;
import com.payvyne.merchant.transaction.entity.Transaction;
import com.payvyne.merchant.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        super();
        this.transactionService = transactionService;
    }

    @GetMapping("/welcome")
    private String welcomeUser()
    {
        return "Welcome, you're logged in. Let's start...";

    }

    @GetMapping("/transactions")
    private Iterable<Transaction> getTransactions()
    {
          return transactionService.getTransactions();
    }

    @GetMapping("/transaction")
    private Iterable<Transaction> getTransactionBasedOnCurrencyandAmount(@RequestParam(value = "currency") String currency,
                                                                         @RequestParam(value = "amount") BigDecimal amount)
    {
        return transactionService.getTransactionBasedOnCurrencyandAmount(currency,amount);
    }

    @GetMapping("/transactions/{transactionId}")
    private Optional<Transaction> getTransactionByID(@PathVariable("transactionId") Long id)
    {
        return transactionService.getTransactionByID(id);
    }

    @GetMapping("/transactions/status/{status}")
    private Iterable<Transaction>  getTransactionByStatus(@PathVariable("status") String transactionStatus)
    {
        return transactionService.getTransactionByStatus(transactionStatus);
    }

    @GetMapping("/transactions/description/{description}")
    private Iterable<Transaction>  getTransactionByDescription(@PathVariable("description") String transactionDescription)
    {
        return transactionService.getTransactionByDescription(transactionDescription);
    }

    @GetMapping("/transactions/amount/{amount}")
    private Iterable<Transaction>  getTransactionByAmount(@PathVariable("amount") BigDecimal transactionAmount)
    {
        return transactionService.getTransactionByAmount(transactionAmount);
    }

    @GetMapping("/transactions/currency/{currency}")
    private Iterable<Transaction>  getTransactionByCurrency(@PathVariable("currency") String transactionCurrency)
    {
        return transactionService.getTransactionByCurrency(transactionCurrency);
    }

    @PostMapping
    private ResponseEntity<String> createTransaction (@RequestBody Transaction transaction)
    {
        try {
            transactionService.createTransaction(transaction);
            return new ResponseEntity<>("Transaction added successfully!", HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(path="/delete/{transactionId}")
    private String deleteTransaction(@PathVariable("transactionId") Long id)
    {
        transactionService.deleteTransaction(id);
        return "Transaction deleted successfully!";
    }

    @PatchMapping(path="/update/{transactionId}")
    private String updateTransaction(@PathVariable("transactionId") Long id,
                                   @RequestBody Transaction updatedtransaction)
    {
        transactionService.updateTransaction(id,updatedtransaction);
        return "Transaction updated successfully!";
    }

}
