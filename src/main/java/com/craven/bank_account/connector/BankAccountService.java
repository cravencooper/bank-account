package com.craven.bank_account.connector;

import com.craven.bank_account.transaction.model.Transaction;

import java.util.List;

public interface BankAccountService {

    void processTransaction(Transaction transaction);

    double retrieveBalance();

    List<Transaction> retrieveAllTransaction();
}
