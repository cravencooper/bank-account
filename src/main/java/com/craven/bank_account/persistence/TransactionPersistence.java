package com.craven.bank_account.persistence;

import com.craven.bank_account.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionPersistence extends JpaRepository<Transaction, Long> {

    @Query("INSERT INTO transaction (id, account_uid, transaction_uid, transaction_type, amount, transaction_time) VALUES (:id, :account_uid, :transaction_uid, :transaction_type, :amount, :transaction_time)")
    void persistTransactionDetails(
            @Param("id") Long id,
            @Param("account_uid") UUID accountUid,
            @Param("transaction_uid") UUID transactionUid,
            @Param("transaction_type") Transaction.TransactionType transactionType,
            @Param("amount") Double amount,
            @Param("transaction_time") Instant transactionTime);
}
