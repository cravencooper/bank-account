package com.craven.bank_account.connector;

import com.craven.bank_account.transaction.TransactionBankAccountService;
import com.craven.bank_account.transaction.model.NewRecord;
import com.craven.bank_account.transaction.model.Transaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bank-account")
public class TransactionService {

    private final TransactionBankAccountService transactionBankAccountService;

    public TransactionService(TransactionBankAccountService transactionBankAccountService) {
        this.transactionBankAccountService = transactionBankAccountService;
    }
    @GetMapping("balance")
    public BigDecimal retrieveBalanceForAccount() {
        return transactionBankAccountService.retrieveBalance();
    }
}
