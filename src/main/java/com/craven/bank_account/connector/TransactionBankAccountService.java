package com.craven.bank_account.connector;

import com.craven.bank_account.audit.AuditService;
import com.craven.bank_account.transaction.model.Transaction;
import com.craven.bank_account.transaction.persistence.TransactionPersistenceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionBankAccountService implements BankAccountService {
    private final TransactionPersistenceService transactionPersistenceService;
    private final AuditService auditService;

    private final AuditServiceConfig auditServiceConfig;

    private static final double MAX_BATCH_VALUE = 1_000_000; // Max value of batch set to £1,000,000
    private static final int BATCH_SIZE_THRESHOLD = 1000; // Maximum value set for the batch size
    private final List<Transaction> currentBatch = new ArrayList<>();
    private double currentBatchValue = 0;

    public TransactionBankAccountService(TransactionPersistenceService transactionPersistenceService, AuditService auditService, AuditServiceConfig auditServiceConfig) {
        this.transactionPersistenceService = transactionPersistenceService;
        this.auditService = auditService;
        this.auditServiceConfig = auditServiceConfig;
    }

    @Override
    public synchronized void processTransaction(Transaction transaction) {
        // Store the transaction
        transactionPersistenceService.storeTransaction(transaction);

        // Calculate the transaction value
        double transactionValue = Math.abs(transaction.getAmount());

        // Update batch value and add transaction to the current batch
        currentBatch.add(transaction);
        currentBatchValue += transactionValue;

        // Check if the batch should be published due to either size or value thresholds
        if (shouldPublishBatch()) {
            publishAndResetBatch();
        }
    }

    @Override
    public double retrieveBalance() {
        return transactionPersistenceService.getTotalBalance();
    }

    @Override
    public List<Transaction> retrieveAllTransaction() {
        return transactionPersistenceService.retrieveAllTransactions();
    }

    private boolean shouldPublishBatch() {
        return currentBatchValue > auditServiceConfig.getBatchSizeThreshold() || currentBatch.size() >= auditServiceConfig.getMaxBatchSize();
    }

    private void publishAndResetBatch() {
        auditService.publishBatch(currentBatch, currentBatchValue);
        currentBatch.clear();
        currentBatchValue = 0;
    }
}
