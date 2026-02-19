package com.pattisian.zetta.bank_backend.timeDeposits.enums;

import java.math.BigDecimal;

public enum PreTerminationPenalty {
    FIRST_QUARTER(25, new BigDecimal("1.00")),
    SECOND_QUARTER(50, new BigDecimal("0.75")),
    THIRD_QUARTER(75, new BigDecimal("0.50")),
    FOURTH_QUARTER(100, new BigDecimal("0.25"));

    private final long termCompletionPercent;
    private final BigDecimal interestForfeited;

    PreTerminationPenalty(int termCompletionPercent, BigDecimal interestForfeited) {
        this.termCompletionPercent = termCompletionPercent;
        this.interestForfeited = interestForfeited;
    }

    public long getTermCompletionPercent() {
        return termCompletionPercent;
    }

    public BigDecimal getInterestForfeited() {
        return interestForfeited;
    }
}
