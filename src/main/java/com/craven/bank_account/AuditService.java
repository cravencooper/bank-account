package com.craven.bank_account;

import com.craven.bank_account.transaction.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuditService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final double MAX_BATCH_VALUE = 1_000_000; // Max value of batch set to £1,000,000
    private static final int BATCH_SIZE_THRESHOLD = 1000;

    private final List<Transaction> currentBatch = new ArrayList<>();
    private double currentBatchValue = 0;
    private int batchNumber = 0;

    public synchronized void addTransaction(Transaction transaction) {
        //Calculate total value of the batch
        double transactionValue = Math.abs(transaction.getAmount());
        //Check to see if conditions are met of either max batch size or the maximum value of a batch.
        if (currentBatchValue + transactionValue > MAX_BATCH_VALUE || currentBatch.size() >= BATCH_SIZE_THRESHOLD) {
            publishBatch();
        }

        currentBatch.add(transaction);
        currentBatchValue += transactionValue;

        if (currentBatch.size() >= BATCH_SIZE_THRESHOLD) {
            publishBatch();
        }
    }

    private void publishBatch() {
        if (!currentBatch.isEmpty()) {
            logger.info("Batch Number {}: ", batchNumber);
            logger.info("Total value: £{}", currentBatchValue);
            logger.info("Count of transactions: {}", currentBatch.size());

            // Reset for next batch
            batchNumber++;
            currentBatch.clear();
            currentBatchValue = 0;
        }
    }
}

