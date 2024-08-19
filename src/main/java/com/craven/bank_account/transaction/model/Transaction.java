package com.craven.bank_account.transaction.model;

import java.util.UUID;

public class Transaction {

    private UUID accountUid;
    private TransactionType transactionType; // "Credit" or "Debit"
    private double amount;

    public Transaction(UUID accountUid, TransactionType transactionType, double amount) {
        this.accountUid = accountUid;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public UUID getAccountUid() {
        return accountUid;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public enum TransactionType {
        CREDIT,
        DEBIT
    }
}
