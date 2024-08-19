package com.craven.bank_account.transaction.model;

import java.math.BigDecimal;
import java.util.UUID;

public record Transaction(
        UUID accountUid,
        UUID transactionUid,
        TransactionType transactionType,
        BigDecimal amount
) {}
