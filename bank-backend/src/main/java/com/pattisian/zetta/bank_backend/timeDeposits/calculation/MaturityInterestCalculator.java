package com.pattisian.zetta.bank_backend.timeDeposits.calculation;

import com.pattisian.zetta.bank_backend.timeDeposits.enums.Term;

import java.math.BigDecimal;

public class MaturityInterestCalculator {

    private MaturityInterestCalculator() {

    }

    public static BigDecimal calculate(BigDecimal principalAmount, Term term) {
        return principalAmount
                .multiply(new BigDecimal("1")
                        .add(term.getInterestRatePerAnnum()));
    }
}
