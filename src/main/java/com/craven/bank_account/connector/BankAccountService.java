package com.craven.bank_account.connector;

import com.craven.bank_account.transaction.model.NewRecord;
import com.craven.bank_account.transaction.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface BankAccountService {

    void processTransaction(NewRecord transaction);

    BigDecimal retrieveBalance();
}
