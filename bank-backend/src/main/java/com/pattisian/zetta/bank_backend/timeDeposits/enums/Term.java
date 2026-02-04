package com.pattisian.zetta.bank_backend.timeDeposits.enums;

import java.math.BigDecimal;

public enum Term {
    THREE_MONTHS(3, new BigDecimal("0.0300")), //3% per annum
    SIX_MONTHS(6, new BigDecimal("0.0450")), // 4.5% per annum
    TWELVE_MONTHS(12, new BigDecimal("0.0600")); // 6% per annum

    private final int months;
    private final BigDecimal interestRatePerAnnum;

    Term(int months, BigDecimal interestRatePerAnnum) {
        this.months = months;
        this.interestRatePerAnnum = interestRatePerAnnum;
    }

    public int getMonths() {
        return months;
    }

    public BigDecimal getInterestRatePerAnnum() {
        return interestRatePerAnnum;
    }
}
