package com.craven.bank_account.connector;

import com.craven.bank_account.transaction.model.Transaction;

import java.math.BigDecimal;

public interface BankAccountService {

    void processTransaction(Transaction transaction);

    BigDecimal retrieveBalance();
}
