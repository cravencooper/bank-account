package com.craven.bank_account.transaction.persistence;

import com.craven.bank_account.transaction.model.Transaction;
import com.craven.bank_account.transaction.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransactionPersistenceServiceTest {

    private TransactionPersistenceService underTest;

    @BeforeEach
    public void setUp() {
        underTest = new TransactionPersistenceService();
    }

    @Test
    public void storeCreditTransaction() {
        // Arrange
        Transaction creditTransaction = mock(Transaction.class);
        when(creditTransaction.transactionType()).thenReturn(TransactionType.CREDIT);
        when(creditTransaction.amount()).thenReturn(BigDecimal.valueOf(100.00));

        // Act
        underTest.storeTransaction(creditTransaction);

        // Assert
        assertThat(underTest.getTotalBalance()).isEqualTo(BigDecimal.valueOf(100.00));
    }

    @Test
    public void storeDebitTransaction() {
        // Arrange
        Transaction debitTransaction = mock(Transaction.class);
        when(debitTransaction.transactionType()).thenReturn(TransactionType.DEBIT);
        when(debitTransaction.amount()).thenReturn(BigDecimal.valueOf(50.00));

        // Act
        underTest.storeTransaction(debitTransaction);

        // Assert
        assertThat(underTest.getTotalBalance()).isEqualTo(BigDecimal.valueOf(-50.00));
    }

    @Test
    public void storeMultipleTransactions() {
        // Arrange
        Transaction creditTransaction = mock(Transaction.class);
        Transaction debitTransaction = mock(Transaction.class);

        when(creditTransaction.transactionType()).thenReturn(TransactionType.CREDIT);
        when(creditTransaction.amount()).thenReturn(BigDecimal.valueOf(150.00));

        when(debitTransaction.transactionType()).thenReturn(TransactionType.DEBIT);
        when(debitTransaction.amount()).thenReturn(BigDecimal.valueOf(50.00));

        // Act
        underTest.storeTransaction(creditTransaction);
        underTest.storeTransaction(debitTransaction);

        // Assert
        assertThat(underTest.getTotalBalance()).isEqualTo(BigDecimal.valueOf(100.00));
    }

    @Test
    public void storeTransactionUpdatesMap() {
        // Arrange
        Transaction transaction = mock(Transaction.class);
        when(transaction.transactionType()).thenReturn(TransactionType.CREDIT);
        when(transaction.amount()).thenReturn(BigDecimal.valueOf(200.00));

        // Act
        underTest.storeTransaction(transaction);

        // Assert
        assertThat(underTest.transactionStorage).hasSize(1);
        assertThat(underTest.transactionStorage.get(0L)).isEqualTo(transaction);
    }
}
