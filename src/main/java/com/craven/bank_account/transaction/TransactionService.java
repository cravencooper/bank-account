package com.craven.bank_account.transaction;

import com.craven.bank_account.connector.TransactionBankAccountService;
import com.craven.bank_account.transaction.model.Transaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bank-account")
public class TransactionService {

    private final TransactionBankAccountService transactionBankAccountService;

    public TransactionService(TransactionBankAccountService transactionBankAccountService) {
        this.transactionBankAccountService = transactionBankAccountService;
    }

    @GetMapping()
    public List<Transaction> retrieveAllTransactions() {
        return transactionBankAccountService.retrieveAllTransaction();
    }

    @GetMapping("balance")
    public double retrieveBalanceForAccount() {
        return transactionBankAccountService.retrieveBalance();
    }
}
