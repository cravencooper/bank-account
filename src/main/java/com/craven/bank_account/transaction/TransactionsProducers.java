package com.craven.bank_account.transaction;

import com.craven.bank_account.transaction.model.NewRecord;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.craven.bank_account.transaction.model.TransactionType.CREDIT;

@Service
public class TransactionsProducers {
    private final Random random = new Random();

    private final UUID accountUid = UUID.randomUUID();
    private final TransactionBankAccountService transactionBankAccountService;

    public TransactionsProducers(TransactionBankAccountService transactionBankAccountService) {
        this.transactionBankAccountService = transactionBankAccountService;
    }

    @Async
    public void generateCredits() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while (true) {
                NewRecord transaction = new NewRecord(accountUid, UUID.randomUUID(), CREDIT, generateRandomAmount(true));
                transactionBankAccountService.processTransaction(transaction);
                try {
                    Thread.sleep(40); // 25 credits per second => 1000ms / 25 = 40ms per transaction
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    @Async
    public void generateDebits() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while (true) {
                NewRecord transaction = new NewRecord(accountUid, UUID.randomUUID(), CREDIT, generateRandomAmount(false));
                transactionBankAccountService.processTransaction(transaction);
                try {
                    Thread.sleep(40); // 25 debits per second => 1000ms / 25 = 40ms per transaction
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    private BigDecimal generateRandomAmount(boolean isCredit) {
        double amount = 20000 + (random.nextDouble() * (500000 - 20000));
        return isCredit ? BigDecimal.valueOf(amount) : BigDecimal.valueOf(amount).negate();
    }
}
