package com.craven.bank_account.transaction;

import com.craven.bank_account.connector.TransactionBankAccountService;
import com.craven.bank_account.transaction.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static com.craven.bank_account.transaction.model.Transaction.TransactionType.*;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

import com.craven.bank_account.connector.TransactionBankAccountService;
import com.craven.bank_account.transaction.TransactionService;
import com.craven.bank_account.transaction.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionBankAccountService transactionBankAccountService;

    @InjectMocks
    private TransactionService transactionService;

    private List<Transaction> mockTransactions;
    private UUID accountUid1;
    private UUID accountUid2;

    @BeforeEach
    void setUp() {
        // Mock transactions setup
        accountUid1 = UUID.randomUUID();
        accountUid2 = UUID.randomUUID();

        Transaction transaction1 = new Transaction(accountUid1, DEBIT, 100.0);
        Transaction transaction2 = new Transaction(accountUid2, CREDIT, 200.0);

        mockTransactions = Arrays.asList(transaction1, transaction2);
    }

    @Test
    void retrieveAllTransactionsForAccount() {
        when(transactionBankAccountService.retrieveAllTransaction()).thenReturn(mockTransactions);

        List<Transaction> transactions = transactionService.retrieveAllTransactions();

        assertEquals(2, transactions.size());
        assertThat(transactions.get(0).getAccountUid()).isEqualTo(accountUid1);
        assertThat(transactions.get(1).getAccountUid()).isEqualTo(accountUid2);

        verify(transactionBankAccountService, times(1)).retrieveAllTransaction();
    }

    @Test
    void retrieveBalanceForAccount() {
        double mockBalance = 300.0;
        when(transactionBankAccountService.retrieveBalance()).thenReturn(mockBalance);

        double balance = transactionService.retrieveBalanceForAccount();

        assertThat(balance).isEqualTo(mockBalance);

        verify(transactionBankAccountService, times(1)).retrieveBalance();
    }
}
