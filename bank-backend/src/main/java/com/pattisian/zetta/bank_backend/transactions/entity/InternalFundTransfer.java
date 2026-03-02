package com.pattisian.zetta.bank_backend.transactions.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.transactions.enums.TransactionType;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class InternalFundTransfer extends Transaction {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id", nullable = false)
    @NotNull
    private Account sourceAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internal_destination_account_id", nullable = false)
    @NotNull
    private Account internalDestinationAccount;

    public InternalFundTransfer() {

    }

    // TransactionType type, User user, BigDecimal amount, BigDecimal balanceBeforeTransaction
    public InternalFundTransfer(Account sourceAccount, Account internalDestinationAccount, User user, BigDecimal amount, BigDecimal balanceAfterTransaction) {
        super(TransactionType.INTERNAL_FUND_TRANSFER, user, amount, sourceAccount.getAvailableBalance(), balanceAfterTransaction);

        this.sourceAccount = sourceAccount;
        this.internalDestinationAccount = internalDestinationAccount;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getInternalDestinationAccount() {
        return internalDestinationAccount;
    }

    public void setInternalDestinationAccount(Account internalDestinationAccount) {
        this.internalDestinationAccount = internalDestinationAccount;
    }
}
