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
public class POS_Purchase extends Transaction {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id",  nullable = false)
    @NotNull
    private Account sourceAccount;

    @Column(name = "purchase_from", nullable = false)
    @NotBlank
    private String purchaseFrom;

    public POS_Purchase() {

    }

    // TransactionType type, User user, BigDecimal amount, BigDecimal balanceBeforeTransaction
    public POS_Purchase(User user, BigDecimal amount, Account sourceAccount, String purchaseFrom, BigDecimal balanceAfterTransaction) {
        super(TransactionType.POS_PURCHASE, user, amount, sourceAccount.getAvailableBalance(), balanceAfterTransaction);

        this.sourceAccount = sourceAccount;
        this.purchaseFrom = purchaseFrom;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getPurchaseFrom() {
        return purchaseFrom;
    }

    public void setPurchaseFrom(String purchaseFrom) {
        this.purchaseFrom = purchaseFrom;
    }
}
