package com.craven.bank_account.transaction;

import com.craven.bank_account.transaction.model.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class TransactionQueueConfig {

    @Bean
    public BlockingQueue<Transaction> transactionQueue() {
        return new LinkedBlockingQueue<>();
    }
}
