package com.craven.bank_account.transaction.persistence;


import com.craven.bank_account.transaction.model.Transaction;

import java.math.BigDecimal;

public interface TransactionPersistence{

    //Purpose of this interface is to allow us to extend functionality easily
    //Currently there is a map, however to move quickly you could extend this by introducing a new
    //bean of an actual database backed persistence layer.

    void storeTransaction(Transaction transaction);
    BigDecimal getTotalBalance();

}
