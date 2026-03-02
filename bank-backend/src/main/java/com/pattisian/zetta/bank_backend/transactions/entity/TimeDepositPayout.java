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
public class TimeDepositPayout extends Transaction {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payout_account_id", nullable = false)
    @NotNull
    private Account payoutAccount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_deposit_id", nullable = false)
    @NotNull
    private TimeDeposit timeDeposit;

    public TimeDepositPayout() {
    }

    public TimeDepositPayout(User user, BigDecimal amount, BigDecimal balanceAfterTransaction, Account payoutAccount, TimeDeposit timeDeposit) {
        super(TransactionType.TIME_DEPOSIT_PAYOUT, user, amount, payoutAccount.getAvailableBalance(), balanceAfterTransaction);
        this.payoutAccount = payoutAccount;
        this.timeDeposit = timeDeposit;
    }

    public Account getPayoutAccount() {
        return payoutAccount;
    }

    public void setPayoutAccount(Account payoutAccount) {
        this.payoutAccount = payoutAccount;
    }

    public TimeDeposit getTimeDeposit() {
        return timeDeposit;
    }

    public void setTimeDeposit(TimeDeposit timeDeposit) {
        this.timeDeposit = timeDeposit;
    }
}
