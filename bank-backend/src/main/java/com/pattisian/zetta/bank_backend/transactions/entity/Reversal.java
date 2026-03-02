package com.pattisian.zetta.bank_backend.transactions.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.transactions.enums.TransactionType;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Reversal extends Transaction {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reversal_account_id", nullable = false)
    @NotNull
    private Account reversalAccount;

    // if reversed, need to update status of reversed tx
    @Column(name = "reversal_reason", nullable = false)
    @NotBlank
    private String reversalReason;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_transaction_id", nullable = false)
    @NotNull
    private Transaction originalTransaction;

    public Reversal() {
    }

    public Reversal(User user, BigDecimal amount, BigDecimal balanceAfterTransaction, Account reversalAccount, String reversalReason, Transaction originalTransaction) {
        super(TransactionType.REVERSAL, user, amount, reversalAccount.getAvailableBalance(), balanceAfterTransaction);
        this.reversalAccount = reversalAccount;
        this.reversalReason = reversalReason;
        this.originalTransaction = originalTransaction;
    }

    public Account getReversalAccount() {
        return reversalAccount;
    }

    public void setReversalAccount(Account reversalAccount) {
        this.reversalAccount = reversalAccount;
    }

    public String getReversalReason() {
        return reversalReason;
    }

    public void setReversalReason(String reversalReason) {
        this.reversalReason = reversalReason;
    }

    public Transaction getOriginalTransaction() {
        return originalTransaction;
    }

    public void setOriginalTransaction(Transaction originalTransaction) {
        this.originalTransaction = originalTransaction;
    }
}
