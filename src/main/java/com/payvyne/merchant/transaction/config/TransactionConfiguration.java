package com.payvyne.merchant.transaction.config;

import com.payvyne.merchant.transaction.constants.TransactionConstants;
import com.payvyne.merchant.transaction.entity.Transaction;
import com.payvyne.merchant.transaction.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class TransactionConfiguration {

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public CommandLineRunner loadTransactions(TransactionRepository transactionRepository)
    {

        return args -> {
            transactionRepository.deleteAll();
            Transaction transact1 =
                    new Transaction(TransactionConstants.COMPLETED,
                    new BigDecimal("1446.25"),
                    TransactionConstants.GBP,
                    "Reference : Kinetic - 2021 Accommodation payment for student code 54638",
                    LocalDateTime.now());
            Transaction transact2 = new Transaction(
                    TransactionConstants.COMPLETED,
                    new BigDecimal("20.00"),
                    TransactionConstants.GBP,
                    "Reference : Kinetic - 2020 room damage charges",
                    LocalDateTime.now());
            transactionRepository.saveAll(List.of(transact1,transact2));
        };
    }


}
