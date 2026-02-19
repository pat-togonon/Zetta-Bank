package com.pattisian.zetta.bank_backend.timeDeposits.calculation;

import com.pattisian.zetta.bank_backend.timeDeposits.enums.PreTerminationPenalty;
import com.pattisian.zetta.bank_backend.timeDeposits.enums.Term;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PreTerminationInterestEarnedCalculator {
    public static final int DAYS_PER_YEAR = 360;

    private PreTerminationInterestEarnedCalculator() {
    }

    public static BigDecimal calculate(BigDecimal principalAmount, long termCompletionPercentage, long daysHeld, Term term) {
        // First 30 days, no penalty. Only DST
        BigDecimal penalty = daysHeld <= 30 ? new BigDecimal("0") : getPreTerminationPenalty(termCompletionPercentage)
                .getInterestForfeited();

        BigDecimal preTermInterest = principalAmount
                                        .multiply(term.getInterestRatePerAnnum())
                                        .multiply(BigDecimal.valueOf(daysHeld))
                                        .divide(BigDecimal.valueOf(DAYS_PER_YEAR), 2, RoundingMode.FLOOR);

       return preTermInterest
               .multiply(BigDecimal.valueOf(1)
                       .subtract(penalty));
    }

    private static PreTerminationPenalty getPreTerminationPenalty(long termCompletionPercentage) {
        PreTerminationPenalty penalty = null;

        if (termCompletionPercentage <= PreTerminationPenalty.FIRST_QUARTER.getTermCompletionPercent()) {
            penalty = PreTerminationPenalty.FIRST_QUARTER;
        } else if (termCompletionPercentage <= PreTerminationPenalty.SECOND_QUARTER.getTermCompletionPercent()) {
            penalty = PreTerminationPenalty.SECOND_QUARTER;
        } else if (termCompletionPercentage <= PreTerminationPenalty.THIRD_QUARTER.getTermCompletionPercent()) {
            penalty = PreTerminationPenalty.THIRD_QUARTER;
        } else if (termCompletionPercentage < PreTerminationPenalty.FOURTH_QUARTER.getTermCompletionPercent()) {
            penalty = PreTerminationPenalty.FOURTH_QUARTER;
        }

        return penalty;
    }


}
