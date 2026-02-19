package com.pattisian.zetta.bank_backend.timeDeposits.calculation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DocumentaryStampTaxCalculator {

    public static final BigDecimal RATE_PER_BASE = new BigDecimal(1.50);
    public static final BigDecimal BASE = new BigDecimal(200);

    private DocumentaryStampTaxCalculator() {
    }

    public static BigDecimal calculate(BigDecimal principal) {
        BigDecimal units = principal
                .divide(BASE, 2, RoundingMode.CEILING);

        return units.multiply(RATE_PER_BASE);
    }
}
