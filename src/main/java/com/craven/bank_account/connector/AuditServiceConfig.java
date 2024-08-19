package com.craven.bank_account.connector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AuditServiceConfig {

    @Value("${max.batch.size}")
    private long maxBatchSize;

    @Value("${batch.size.threshold}")
    private long batchSizeThreshold;

    public long getMaxBatchSize() {
        return maxBatchSize;
    }

    public long getBatchSizeThreshold() {
        return batchSizeThreshold;
    }
}
