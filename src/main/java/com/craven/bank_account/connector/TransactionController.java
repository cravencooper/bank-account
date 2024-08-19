package com.craven.bank_account.connector;

import com.craven.bank_account.transaction.TransactionBankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/bank-account")
public class TransactionController {

    private final TransactionBankAccountService transactionBankAccountService;

    public TransactionController(TransactionBankAccountService transactionBankAccountService) {
        this.transactionBankAccountService = transactionBankAccountService;
    }
    @GetMapping("balance")
    public BigDecimal retrieveBalanceForAccount() {
        //As the spec says, we can be reassured that we only use a single account, personally I would
        //add a Request body which contains things like transaction time, traceId for immutability
        //as well as AccountUid.
        return transactionBankAccountService.retrieveBalance();
    }
}
