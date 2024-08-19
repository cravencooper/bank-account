package com.craven.bank_account.connector;

import com.craven.bank_account.transaction.model.Transaction;

public interface TransactionResource {

    void processTransaction(Transaction transaction);

}
