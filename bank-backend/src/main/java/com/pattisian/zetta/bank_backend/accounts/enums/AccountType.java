package com.pattisian.zetta.bank_backend.accounts.enums;

import java.math.BigDecimal;

public enum AccountType {
    SAVINGS(new BigDecimal("0.0005")), // 0.05% per annum
    CHECKING(new BigDecimal("0.0000"));

    private final BigDecimal interestRate;

    AccountType(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }



}
