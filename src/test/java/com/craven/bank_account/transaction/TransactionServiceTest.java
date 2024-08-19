package com.craven.bank_account.transaction;

import com.craven.bank_account.connector.TransactionBankAccountService;
import com.craven.bank_account.transaction.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static com.craven.bank_account.transaction.model.Transaction.TransactionType.*;
import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class TransactionServiceTest {

    @Autowired
    private TransactionService underTest;

    @Mock
    private TransactionBankAccountService transactionBankAccountService;

    @BeforeEach
    void setUp() {
        openMocks(this);

        underTest = new TransactionService(transactionBankAccountService);
    }
}