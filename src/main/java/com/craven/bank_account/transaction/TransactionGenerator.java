package com.craven.bank_account.transaction;

import com.craven.bank_account.transaction.model.Transaction;
import com.craven.bank_account.transaction.model.Transaction.TransactionType;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.craven.bank_account.transaction.model.Transaction.TransactionType.CREDIT;
import static com.craven.bank_account.transaction.model.Transaction.TransactionType.DEBIT;

@Component
public class TransactionGenerator {
    private static final double MIN_AMOUNT = 20000.0;
    private static final double MAX_AMOUNT = 500000.0;
    private static final Random random = new Random();

    public List<Transaction> generateTransactions() {
        int numTransactions = random.nextInt(1000) + 1;

        // Generate and print a sample of 5 transactions
        List<Transaction> transactions = new ArrayList<>(numTransactions);
        for (int i = 0; i < numTransactions; i++) {
            transactions.add(generateTransaction());
        }
        return transactions;
    }

    /*public double retrieveBalance() {
        int numTransactions = random.nextInt(1000) + 1;

        // Generate and print a sample of 5 transactions
        List<Transaction> transactions = new ArrayList<>(numTransactions);
        for (int i = 0; i < numTransactions; i++) {
            transactions.add(generateTransaction());
        }


    }*/

    private Transaction generateTransaction() {
        // Generate a random UUID for transaction ID
        UUID id = UUID.randomUUID();

        // Randomly determine if the transaction is a credit or debit
        TransactionType type = random.nextBoolean() ? CREDIT : DEBIT;

        // Generate a random amount between MIN_AMOUNT and MAX_AMOUNT
        double amount = MIN_AMOUNT + (MAX_AMOUNT - MIN_AMOUNT) * random.nextDouble();

        // Create and return the transaction
        return new Transaction(id, type, amount);
    }
}
