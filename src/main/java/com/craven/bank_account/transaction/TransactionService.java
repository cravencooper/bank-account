package com.craven.bank_account.transaction;

import com.craven.bank_account.connector.TransactionResource;
import com.craven.bank_account.persistence.TransactionPersistence;
import com.craven.bank_account.transaction.model.Transaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/account/transactions")
public class TransactionService implements TransactionResource {

    private final TransactionPersistence transactionPersistenceService;

    public TransactionService(TransactionGenerator transactionGenerator, TransactionPersistence transactionPersistenceService) {
        this.transactionPersistenceService = transactionPersistenceService;
    }

    @GetMapping()
    public List<Transaction> retrieveAllTransactions() {
return null;    }

    @Override
    public void processTransaction(Transaction transaction) {
        transactionPersistenceService.persistTransactionDetails(
                transaction.getId(),
                transaction.getAccountUid(),
                UUID.randomUUID(),
                transaction.getType(),
                transaction.getAmount(),
                Instant.now()
        );
    }
}
