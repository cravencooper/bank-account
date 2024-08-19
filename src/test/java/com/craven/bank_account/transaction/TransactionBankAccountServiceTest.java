package com.craven.bank_account.transaction;

import com.craven.bank_account.audit.AuditService;
import com.craven.bank_account.audit.AuditServiceConfig;
import com.craven.bank_account.transaction.model.Transaction;
import com.craven.bank_account.transaction.persistence.TransactionPersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

import static com.craven.bank_account.transaction.model.TransactionType.CREDIT;
import static com.craven.bank_account.transaction.model.TransactionType.DEBIT;
import static org.assertj.core.api.Assertions.assertThat;
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
    private UUID accountUid1;
    private UUID accountUid2;
    private Transaction transaction1;
    private Transaction transaction2;

    @BeforeEach
    void setUp() {
        openMocks(this);

        underTest = new TransactionBankAccountService(transactionPersistenceService, auditService, auditServiceConfig);

        accountUid1 = UUID.randomUUID();
        accountUid2 = UUID.randomUUID();

        transaction1 = new Transaction(accountUid1, UUID.randomUUID(), DEBIT, BigDecimal.valueOf(100.0));
        transaction2 = new Transaction(accountUid2, UUID.randomUUID(), CREDIT, BigDecimal.valueOf(200.0));
    }

    @Test
    void willStoreProvidedTransaction() {
        transaction1 = new Transaction(accountUid1, UUID.randomUUID(), DEBIT, BigDecimal.valueOf(123.4));

        underTest.processTransaction(transaction1);

        verify(transactionPersistenceService).storeTransaction(transaction1);
    }

    @Test
    void willNotSendBatchToAuditService_whenCurrentBatchSizeIsSmallerThanThreshold() {
        when(auditServiceConfig.getMaxBatchSize()).thenReturn(2l);
        when(auditServiceConfig.getBatchSizeThreshold()).thenReturn(1000l);
        Transaction transaction = new Transaction(accountUid1, UUID.randomUUID(), DEBIT, BigDecimal.valueOf(123.34));


        underTest.processTransaction(transaction);

        verifyNoInteractions(auditService);
    }

    @Test
    void willSendBatchToAuditService_whenCurrentBatchSizeIsMeetsConfiguredValue() {
        when(auditServiceConfig.getMaxBatchSize()).thenReturn(2l);
        when(auditServiceConfig.getBatchSizeThreshold()).thenReturn(1000l);
        BigDecimal transaction1Amount = BigDecimal.valueOf(100.50);
        BigDecimal transaction2Amount = BigDecimal.valueOf(100.50);

        Transaction transaction1 = new Transaction(accountUid1, UUID.randomUUID(), DEBIT, transaction1Amount);
        Transaction transaction2 = new Transaction(accountUid1, UUID.randomUUID(), DEBIT, transaction2Amount);

        underTest.processTransaction(transaction1);

        underTest.processTransaction(transaction2);

        verify(auditService).publishBatch(anyList(), eq(BigDecimal.valueOf(201.0)));
    }

    @Test
    void willSendBatchToAuditService_whenBatchSizeThresholdExceedsConfiguredValue() {
        when(auditServiceConfig.getMaxBatchSize()).thenReturn(3l);
        when(auditServiceConfig.getBatchSizeThreshold()).thenReturn(100l);
        BigDecimal transaction1Amount = BigDecimal.valueOf(99);
        BigDecimal transaction2Amount = BigDecimal.valueOf(2);

        Transaction transaction1 = new Transaction(accountUid1, UUID.randomUUID(), DEBIT, transaction1Amount);
        Transaction transaction2 = new Transaction(accountUid1, UUID.randomUUID(), DEBIT, transaction2Amount);

        underTest.processTransaction(transaction1);

        underTest.processTransaction(transaction2);

        verify(auditService).publishBatch(anyList(), eq(BigDecimal.valueOf(101)));
    }

    @Test
    void willRetrieveBalance() {
        // Arrange
        when(transactionPersistenceService.getTotalBalance()).thenReturn(BigDecimal.valueOf(1000.0));

        // Act
        BigDecimal balance = underTest.retrieveBalance();

        // Assert
        assertThat(balance).isEqualTo(BigDecimal.valueOf(1000.0));
        verify(transactionPersistenceService).getTotalBalance();
    }
}