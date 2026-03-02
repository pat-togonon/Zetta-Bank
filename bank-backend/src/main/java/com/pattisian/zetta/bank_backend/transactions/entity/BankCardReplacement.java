package com.pattisian.zetta.bank_backend.transactions.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.bankcards.entity.BankCard;
import com.pattisian.zetta.bank_backend.transactions.enums.TransactionType;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class BankCardReplacement extends Transaction {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_bank_card_id", nullable = false)
    @NotNull
    private BankCard newBankCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @NotNull
    private Account account;


    public BankCardReplacement() {

    }

    public BankCardReplacement(User user, BigDecimal amount, Account account, BigDecimal balanceAfterTransaction, BankCard newBankCard) {
        super(TransactionType.BANK_CARD_REPLACEMENT, user, amount, account.getAvailableBalance(), balanceAfterTransaction);
        this.newBankCard = newBankCard;
    }

    public BankCard getNewBankCard() {
        return newBankCard;
    }

    public void setNewBankCard(BankCard newBankCard) {
        this.newBankCard = newBankCard;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
