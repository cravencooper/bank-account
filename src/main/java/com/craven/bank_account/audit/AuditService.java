package com.craven.bank_account.audit;

import com.craven.bank_account.transaction.model.NewRecord;
import com.craven.bank_account.transaction.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AuditService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private int batchNumber = 0;

    public void publishBatch(List<NewRecord> currentBatch, BigDecimal currentBatchValue) {
        if (!currentBatch.isEmpty()) {
            logger.info("Batch Number {}: ", batchNumber);
            logger.info("Total value: £{}", currentBatchValue);
            logger.info("Count of transactions: {}", currentBatch.size());
            batchNumber++;
        }
    }
}

