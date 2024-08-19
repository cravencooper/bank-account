package com.craven.bank_account.transaction.persistence;


import com.craven.bank_account.transaction.model.Transaction;

import java.math.BigDecimal;

public interface TransactionPersistence{

    void storeTransaction(Transaction transaction);
    BigDecimal getTotalBalance();

}
