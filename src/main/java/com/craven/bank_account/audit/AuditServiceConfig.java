package com.craven.bank_account.audit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
