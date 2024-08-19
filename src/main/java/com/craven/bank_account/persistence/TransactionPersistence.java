package com.craven.bank_account.persistence;


import com.craven.bank_account.transaction.model.Transaction;

import java.util.List;

public interface TransactionPersistence{

    void storeTransaction(Transaction transaction);

    List<Transaction> retrieveAllTransactions();

}
