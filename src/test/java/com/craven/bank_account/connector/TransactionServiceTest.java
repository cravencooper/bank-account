package com.craven.bank_account.connector;

import com.craven.bank_account.transaction.TransactionBankAccountService;
import com.craven.bank_account.transaction.model.NewRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static com.craven.bank_account.transaction.model.TransactionType.CREDIT;
import static com.craven.bank_account.transaction.model.TransactionType.DEBIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionBankAccountService transactionBankAccountService;
    @Autowired
    private TransactionService underTest;

    private List<NewRecord> mockTransactions;
    private UUID accountUid1;
    private UUID accountUid2;

    @BeforeEach
    void setUp() {
        // Mock transactions setup
        accountUid1 = UUID.randomUUID();
        accountUid2 = UUID.randomUUID();

        NewRecord transaction1 = new NewRecord(accountUid1,  UUID.randomUUID(), DEBIT, BigDecimal.valueOf(100.0));
        NewRecord transaction2 = new NewRecord(accountUid2,   UUID.randomUUID(), CREDIT,  BigDecimal.valueOf(200.0));

        mockTransactions = Arrays.asList(transaction1, transaction2);

        underTest = new TransactionService(transactionBankAccountService);
    }

    @Test
    void retrieveAllTransactionsForAccount() {
        when(transactionBankAccountService.retrieveAllTransaction()).thenReturn(mockTransactions);

        List<NewRecord> transactions = underTest.retrieveAllTransactions();

        assertEquals(2, transactions.size());
        assertThat(transactions.get(0).accountUid()).isEqualTo(accountUid1);
        assertThat(transactions.get(1).accountUid()).isEqualTo(accountUid2);

        verify(transactionBankAccountService, times(1)).retrieveAllTransaction();
    }

    @Test
    void retrieveBalanceForAccount() {
        BigDecimal mockBalance = BigDecimal.valueOf(300.0);
        when(transactionBankAccountService.retrieveBalance()).thenReturn(mockBalance);

        BigDecimal balance = underTest.retrieveBalanceForAccount();

        assertThat(balance).isEqualTo(mockBalance);

        verify(transactionBankAccountService, times(1)).retrieveBalance();
    }
}
