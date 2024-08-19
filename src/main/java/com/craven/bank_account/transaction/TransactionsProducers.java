package com.craven.bank_account.transaction;

import com.craven.bank_account.AuditService;
import com.craven.bank_account.persistence.TransactionPersistence;
import com.craven.bank_account.transaction.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.craven.bank_account.transaction.model.Transaction.TransactionType.CREDIT;
import static com.craven.bank_account.transaction.model.Transaction.TransactionType.DEBIT;

@Service
public class TransactionsProducers {
    private final Logger logger = LoggerFactory.getLogger(getClass());    private final Random random = new Random();

    private final TransactionPersistence transactionPersistence;
    private final AuditService auditService;

    public TransactionsProducers(TransactionPersistence transactionPersistence, AuditService auditService) {
        this.transactionPersistence = transactionPersistence;
        this.auditService = auditService;
    }

    @Async
    public void generateCredits() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while (true) {
                Transaction transaction = new Transaction(UUID.randomUUID(), CREDIT, generateRandomAmount(true));
                logger.info("Generated Credit: " + transaction);
                transactionPersistence.storeTransaction(transaction);
                auditService.addTransaction(transaction);
                try {
                    Thread.sleep(40); // 25 credits per second => 1000ms / 25 = 40ms per transaction
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    @Async
    public void generateDebits() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while (true) {
                Transaction transaction = new Transaction(UUID.randomUUID(), DEBIT, generateRandomAmount(false));
                logger.info("Generated Debit: " + transaction);
                transactionPersistence.storeTransaction(transaction);
                auditService.addTransaction(transaction);
                try {
                    Thread.sleep(40); // 25 debits per second => 1000ms / 25 = 40ms per transaction
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    private double generateRandomAmount(boolean isCredit) {
        double amount = 20000 + (random.nextDouble() * (500000 - 20000));
        return isCredit ? amount : -amount;
    }
}
