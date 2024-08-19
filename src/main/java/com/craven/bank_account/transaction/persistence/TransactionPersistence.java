package com.craven.bank_account.transaction.persistence;


import com.craven.bank_account.transaction.model.NewRecord;
import com.craven.bank_account.transaction.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionPersistence{

    void storeTransaction(NewRecord transaction);
    BigDecimal getTotalBalance();

}
