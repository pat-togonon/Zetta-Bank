package com.pattisian.zetta.bank_backend.accounts.dto;

import com.pattisian.zetta.bank_backend.accounts.enums.AccountType;
import com.pattisian.zetta.bank_backend.common.enums.Currency;

import java.math.BigDecimal;

public class NewAccountRequestDTO {

    private AccountType accountType;
    private BigDecimal availableBalance;
    private Currency currency;

    public NewAccountRequestDTO(AccountType accountType, BigDecimal availableBalance, Currency currency) {
        this.accountType = accountType;
        this.availableBalance = availableBalance;
        this.currency = currency;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "NewAccountRequestDTO{" +
                "accountType=" + accountType +
                ", availableBalance=" + availableBalance +
                ", currency=" + currency +
                '}';
    }
}
