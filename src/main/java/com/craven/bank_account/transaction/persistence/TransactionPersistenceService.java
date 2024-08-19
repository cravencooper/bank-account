package com.craven.bank_account.transaction.persistence;

import com.craven.bank_account.transaction.model.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.craven.bank_account.transaction.model.Transaction.TransactionType.CREDIT;
import static com.craven.bank_account.transaction.model.Transaction.TransactionType.DEBIT;

@Repository
public class TransactionPersistenceService implements TransactionPersistence {

    Map<Long, Transaction> transactionStorage = new HashMap<>();

    @Override
    public void storeTransaction(Transaction transaction) {
        Long id = 0L;
        // Store the transaction in the map

        transactionStorage.put(id, transaction);

        // Update the total balance based on the type of transaction
        if (transaction.getTransactionType() == CREDIT) {
            totalBalance += transaction.getAmount();
        } else if (transaction.getTransactionType() == DEBIT) {
            totalBalance -= transaction.getAmount();
        }
    }

    @Override
    public List<Transaction> retrieveAllTransactions() {
        return transactionStorage.values().stream().toList();
    }
    private double totalBalance = 0.0;


    public Transaction getTransactionById(String id) {
        return transactionStorage.get(id);
    }

    public double getTotalBalance() {
        return totalBalance;
    }
}
