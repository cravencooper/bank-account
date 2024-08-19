package com.craven.bank_account.transaction;

import com.craven.bank_account.audit.AuditService;
import com.craven.bank_account.connector.AuditServiceConfig;
import com.craven.bank_account.connector.BankAccountService;
import com.craven.bank_account.transaction.model.NewRecord;
import com.craven.bank_account.transaction.model.Transaction;
import com.craven.bank_account.transaction.persistence.TransactionPersistenceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionBankAccountService implements BankAccountService {
    private final TransactionPersistenceService transactionPersistenceService;
    private final AuditService auditService;
    private final AuditServiceConfig auditServiceConfig;
    private final List<NewRecord> currentBatch = new ArrayList<>();
    private BigDecimal currentBatchValue;

    public TransactionBankAccountService(TransactionPersistenceService transactionPersistenceService, AuditService auditService, AuditServiceConfig auditServiceConfig) {
        this.transactionPersistenceService = transactionPersistenceService;
        this.auditService = auditService;
        this.auditServiceConfig = auditServiceConfig;
        this.currentBatchValue = new BigDecimal(0);
    }

    @Override
    public synchronized void processTransaction(NewRecord transaction) {
        // Store the transaction
        transactionPersistenceService.storeTransaction(transaction);

        // Calculate the transaction value
        BigDecimal transactionValue = transaction.amount();

        // Update batch value and add transaction to the current batch
        currentBatch.add(transaction);
        currentBatchValue = currentBatchValue.add(transactionValue);

        // Check if the batch should be published due to either size or value thresholds
        if (shouldPublishBatch()) {
            publishAndResetBatch();
        }
    }

    @Override
    public BigDecimal retrieveBalance() {
        return transactionPersistenceService.getTotalBalance();
    }

    private boolean shouldPublishBatch() {
        return currentBatchValue.compareTo(BigDecimal.valueOf(auditServiceConfig.getBatchSizeThreshold())) > 0
                || currentBatch.size() >= auditServiceConfig.getMaxBatchSize();
    }

    private void publishAndResetBatch() {
        auditService.publishBatch(currentBatch, currentBatchValue);
        currentBatch.clear();
        currentBatchValue = BigDecimal.ZERO;
    }
}
