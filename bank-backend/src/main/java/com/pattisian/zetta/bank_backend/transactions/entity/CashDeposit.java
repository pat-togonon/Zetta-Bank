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
public class CashDeposit extends Transaction {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id", nullable = false)
    @NotNull
    private Account sourceAccount;

    @Column(nullable = false)
    @NotBlank
    private String location;

    public CashDeposit() {
    }

    public CashDeposit(User user, BigDecimal amount, BigDecimal balanceAfterTransaction, Account sourceAccount, String location) {
        super(TransactionType.CASH_DEPOSIT, user, amount, sourceAccount.getAvailableBalance(), balanceAfterTransaction);
        this.sourceAccount = sourceAccount;
        this.location = location;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
