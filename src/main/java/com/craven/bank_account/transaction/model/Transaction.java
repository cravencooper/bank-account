package com.craven.bank_account.transaction.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

public class Transaction {

    private UUID accountUid;
    private TransactionType type; // "Credit" or "Debit"
    private double amount;

    public Transaction(UUID accountUid, TransactionType type, double amount) {
        this.accountUid = accountUid;
        this.type = type;
        this.amount = amount;
    }

    public UUID getAccountUid() {
        return accountUid;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }


    public enum TransactionType {
        CREDIT,
        DEBIT

    }
}
