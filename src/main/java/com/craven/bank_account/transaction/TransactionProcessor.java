package com.craven.bank_account.transaction;


import com.craven.bank_account.transaction.model.Transaction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;


@Service
public class TransactionProcessor {

    private final BlockingQueue<Transaction> transactionQueue;

    private final TransactionService transactionService;

    public TransactionProcessor(BlockingQueue<Transaction> transactionQueue, TransactionService transactionService) {
        this.transactionQueue = transactionQueue;
        this.transactionService = transactionService;
    }

    @Scheduled(fixedDelay = 1000)
    public void processTransactions() {
        try {
            Transaction transaction = transactionQueue.take();
            transactionService.processTransaction(transaction);
            System.out.println("Processed: " + transaction);
            // Add logic to process the transaction (e.g., update account balance)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

