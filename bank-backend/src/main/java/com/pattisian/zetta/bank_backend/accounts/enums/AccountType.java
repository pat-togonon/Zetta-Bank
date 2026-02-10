package com.pattisian.zetta.bank_backend.accounts.enums;

import java.math.BigDecimal;

public enum AccountType {
    SAVINGS(new BigDecimal("0.0005"), 2, "SV"), // 0.05% per annum
    CHECKING(new BigDecimal("0.0000"), 1, "CH");

    private final BigDecimal interestRate;
    private final int maxCount;
    private final String accountTypeCode;

    AccountType(BigDecimal interestRate, int maxCount, String accountTypeCode) {
        this.interestRate = interestRate;
        this.maxCount = maxCount;
        this.accountTypeCode = accountTypeCode;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public String getAccountTypeCode() {
        return accountTypeCode;
    }
}
