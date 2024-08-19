package com.craven.bank_account.transaction.persistence;

import com.craven.bank_account.transaction.model.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.craven.bank_account.transaction.model.TransactionType.CREDIT;
import static com.craven.bank_account.transaction.model.TransactionType.DEBIT;

@Repository
public class TransactionPersistenceService implements TransactionPersistence {

    Map<Long, Transaction> transactionStorage = new ConcurrentHashMap<>();

    BigDecimal totalBalance = BigDecimal.ZERO; // Initialize totalBalance

    @Override
    public void storeTransaction(Transaction transaction) {
        Long id = 0L;
        // Store the transaction in the map - This should be backed by a DB and would be future enhancement.
        transactionStorage.put(id, transaction);

        // Update the total balance based on the type of transaction - CREDIT will add to balance, DEBIT will remove.
        if (transaction.transactionType() == CREDIT) {
            totalBalance = totalBalance.add(transaction.amount());
        } else if (transaction.transactionType() == DEBIT) {
            totalBalance = totalBalance.subtract(transaction.amount());
        }
    }
    @Override
    public BigDecimal getTotalBalance() {
        return totalBalance;
    }
}
