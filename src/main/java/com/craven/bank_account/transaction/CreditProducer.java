package com.craven.bank_account.transaction;


import com.craven.bank_account.transaction.model.Transaction;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.craven.bank_account.transaction.model.Transaction.TransactionType.*;

@Service
public class CreditProducer {
    private static final double TRANSACTION_RATE_PER_SECOND = 25.0;
    private static final long SLEEP_TIME_MS = (long) (1000 / TRANSACTION_RATE_PER_SECOND);
    private static final double MIN_AMOUNT = 20000.0;
    private static final double MAX_AMOUNT = 500000.0;
    private static final Random random = new Random();

    private final BlockingQueue<Transaction> transactionQueue;

    public CreditProducer(BlockingQueue<Transaction> transactionQueue) {
        this.transactionQueue = transactionQueue;
    }

    @Async
    public void produceCredits() throws RuntimeException {
        try {
            while (true) {
                double amount = MIN_AMOUNT + (MAX_AMOUNT - MIN_AMOUNT) * random.nextDouble();
                Transaction transaction = new Transaction(UUID.randomUUID(), CREDIT, amount);
                transactionQueue.put(transaction);
                System.out.println("Produced Credit: " + transaction);

                // Sleep for the calculated time to achieve 25 transactions per second
                TimeUnit.MILLISECONDS.sleep(SLEEP_TIME_MS);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e.getMessage());
        }
    }
}

