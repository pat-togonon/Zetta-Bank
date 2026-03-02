package com.pattisian.zetta.bank_backend.transactions.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.timeDeposits.entity.TimeDeposit;
import com.pattisian.zetta.bank_backend.transactions.enums.TransactionType;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class TimeDepositOpening extends Transaction {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id", nullable = false)
    @NotNull
    private Account sourceAccount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_deposit_id", nullable = false)
    @NotNull
    private TimeDeposit timeDeposit;

    public TimeDepositOpening() {
    }

    public TimeDepositOpening(User user, BigDecimal amount, BigDecimal balanceAfterTransaction, Account sourceAccount, TimeDeposit timeDeposit) {
        super(TransactionType.TIME_DEPOSIT_OPENING, user, amount, sourceAccount.getAvailableBalance(), balanceAfterTransaction);
        this.sourceAccount = sourceAccount;
        this.timeDeposit = timeDeposit;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public TimeDeposit getTimeDeposit() {
        return timeDeposit;
    }

    public void setTimeDeposit(TimeDeposit timeDeposit) {
        this.timeDeposit = timeDeposit;
    }
}
