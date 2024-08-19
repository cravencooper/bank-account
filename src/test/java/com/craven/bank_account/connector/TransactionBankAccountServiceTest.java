package com.craven.bank_account.connector;

import com.craven.bank_account.audit.AuditService;
import com.craven.bank_account.transaction.model.Transaction;
import com.craven.bank_account.transaction.persistence.TransactionPersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.craven.bank_account.transaction.model.Transaction.TransactionType.DEBIT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class TransactionBankAccountServiceTest {

    @Autowired
    private TransactionBankAccountService underTest;
    @Mock
    private TransactionPersistenceService transactionPersistenceService;
    @Mock
    private AuditService auditService;
    @Mock
    private AuditServiceConfig auditServiceConfig;

    @BeforeEach
    void setUp() {
        openMocks(this);

        underTest = new TransactionBankAccountService(transactionPersistenceService, auditService, auditServiceConfig);
    }

    @Test
    void willStoreProvidedTransaction() {
        Transaction transaction = new Transaction(UUID.randomUUID(), DEBIT, 123.34);

        underTest.processTransaction(transaction);

        verify(transactionPersistenceService).storeTransaction(transaction);
    }

    @Test
    void willNotSendBatchToAuditService_whenCurrentBatchSizeIsSmallerThanThreshold() {
        when(auditServiceConfig.getMaxBatchSize()).thenReturn(2L);
        when(auditServiceConfig.getBatchSizeThreshold()).thenReturn(1000L);
        Transaction transaction = new Transaction(UUID.randomUUID(), DEBIT, 123.34);

        underTest.processTransaction(transaction);

        verifyNoInteractions(auditService);

    }
}