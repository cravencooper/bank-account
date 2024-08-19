package com.craven.bank_account.transaction.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Optional: auto-generates ID
    private Long id;
    private UUID accountUid;
    private TransactionType type; // "Credit" or "Debit"
    private double amount;

    public Transaction(UUID accountUid, TransactionType type, double amount) {
        this.accountUid = accountUid;
        this.type = type;
        this.amount = amount;
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountUid(UUID accountUid) {
        this.accountUid = accountUid;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
