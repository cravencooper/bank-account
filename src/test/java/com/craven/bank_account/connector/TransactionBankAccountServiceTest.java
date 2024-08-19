package com.craven.bank_account.connector;

import com.craven.bank_account.audit.AuditService;
import com.craven.bank_account.transaction.model.Transaction;
import com.craven.bank_account.transaction.persistence.TransactionPersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.craven.bank_account.transaction.model.Transaction.TransactionType.DEBIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class TransactionBankAccountServiceTest {

    @Autowired
    private TransactionBankAccountService underTest;
    @Mock
    private TransactionPersistenceService transactionPersistenceService;
    @Spy
    private AuditService auditService;
    @Mock
    private AuditServiceConfig auditServiceConfig;
    @Captor
    private ArgumentCaptor<List<Transaction>> transactionsCaptor;

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

    @Test
    void willSendBatchToAuditService_whenCurrentBatchSizeIsMeetsConfiguredValue() {
        when(auditServiceConfig.getMaxBatchSize()).thenReturn(2L);
        when(auditServiceConfig.getBatchSizeThreshold()).thenReturn(1000L);
        double transaction1Amount = 100.50;
        double transaction2Amount = 100.50;

        Transaction transaction1 = new Transaction(UUID.randomUUID(), DEBIT, transaction1Amount);
        Transaction transaction2 = new Transaction(UUID.randomUUID(), DEBIT, transaction2Amount);

        List<Transaction> expectedBatch = Arrays.asList(transaction1, transaction2);

        underTest.processTransaction(transaction1);

        underTest.processTransaction(transaction2);

        verify(auditService).publishBatch(expectedBatch, 201D);
    }
}