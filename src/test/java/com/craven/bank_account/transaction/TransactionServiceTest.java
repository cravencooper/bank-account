package com.craven.bank_account.transaction;

import com.craven.bank_account.persistence.TransactionPersistence;
import com.craven.bank_account.transaction.model.Transaction;
import com.craven.bank_account.transaction.model.Transaction.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.craven.bank_account.transaction.model.Transaction.TransactionType.*;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class TransactionServiceTest {

    @Autowired
    private TransactionService underTest;

    @Mock
    private TransactionPersistence transactionPersistence;

    @BeforeEach
    void setUp() {
        openMocks(this);

        underTest = new TransactionService(null, transactionPersistence);
    }

    @Test
    void willPersistProvidedTransaction() {
        Transaction transaction = new Transaction(randomUUID(), DEBIT, 123);

        underTest.processTransaction(transaction);
    }
}