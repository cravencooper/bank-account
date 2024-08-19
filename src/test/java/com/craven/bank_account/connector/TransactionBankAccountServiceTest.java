package com.craven.bank_account.connector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

class TransactionBankAccountServiceTest {

    @Autowired
    private TransactionBankAccountService underTest;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void willStoreProvidedTransaction() {

    }
}