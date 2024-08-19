package com.craven.bank_account.persistence;

import com.craven.bank_account.transaction.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TransactionPersistenceService implements TransactionPersistence {

    Map<UUID, Transaction> transactionStorage = new HashMap<>();

    @Override
    public void storeTransaction(Transaction transaction) {
        UUID transactionUid = UUID.randomUUID();
        transactionStorage.put(transactionUid, transaction);
    }

    @Override
    public List<Transaction> retrieveAllTransactions() {
        return transactionStorage.values().stream().toList();
    }
}
