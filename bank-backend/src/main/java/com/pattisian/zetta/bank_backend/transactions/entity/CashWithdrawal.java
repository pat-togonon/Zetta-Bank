package com.pattisian.zetta.bank_backend.transactions.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.transactions.enums.CashWithdrawalType;
import com.pattisian.zetta.bank_backend.transactions.enums.TransactionType;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CashWithdrawal extends Transaction {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id", nullable = false)
    @NotNull
    private Account sourceAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "cash_withdrawal_type", nullable = false)
    @NotNull
    private CashWithdrawalType cashWithdrawalType;

    @Column(nullable = false)
    @NotBlank
    private String location;

    public CashWithdrawal() {
    }

    // TransactionType type, User user, BigDecimal amount, BigDecimal balanceBeforeTransaction
    public CashWithdrawal(User user, BigDecimal amount, Account sourceAccount, CashWithdrawalType cashWithdrawalType, String location, BigDecimal balanceAfterTransaction) {
        super(TransactionType.CASH_WITHDRAWAL, user, amount, sourceAccount.getAvailableBalance(), balanceAfterTransaction);

        this.sourceAccount = sourceAccount;
        this.cashWithdrawalType = cashWithdrawalType;
        this.location = location;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public CashWithdrawalType getCashWithdrawalType() {
        return cashWithdrawalType;
    }

    public void setCashWithdrawalType(CashWithdrawalType cashWithdrawalType) {
        this.cashWithdrawalType = cashWithdrawalType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
