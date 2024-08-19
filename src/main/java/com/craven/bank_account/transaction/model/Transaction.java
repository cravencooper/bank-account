package com.craven.bank_account.transaction.model;

import java.math.BigDecimal;
import java.util.UUID;

public record Transaction(
        UUID accountUid,
        UUID transactionUid,
        TransactionType transactionType,

        //This is a big decimal just now, however I would extend this to have a Money class that contains
        // BigDecimal for amount, Minor or Major unit as well as the currency
        BigDecimal amount
) {}
