package com.pattisian.zetta.bank_backend.common.helpers;

import com.pattisian.zetta.bank_backend.common.ConstantValues;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

public final class Helper {

    //Can't create instance of this class
    private Helper() {

    }

    public static String generateCvv() {
        UUID uuid = UUID.randomUUID();
        return String.valueOf(Math.abs(uuid.getMostSignificantBits())).substring(0, 3);
    }

    public static String generateNumberSequence() {
        UUID uuid = UUID.randomUUID();
        return String.valueOf(Math.abs(uuid.getMostSignificantBits())).substring(0, 9);
    }

    public static String generateAccountNumber(String accountTypeCode, String bankCode, String branchCode, Instant dateOpened) {

        String sequence = generateNumberSequence();
        int yearOpened = extractYear(dateOpened);
        String accountNumber = String.format("%s-%s-%s-%s-%s", accountTypeCode, bankCode, branchCode, yearOpened, sequence);

        return accountNumber;
    }

    public static int extractYear(Instant date) {
        ZonedDateTime zdt = date.atZone(ConstantValues.BANK_ZONE);
        return zdt.getYear();
    }

    public static int calculateCheckDigit(String payloadDigits) {
        int newValue;
        int sum = 0;
        for (int i = payloadDigits.length() - 1; i >= 0; i--) {
            int num = Integer.parseInt(payloadDigits.substring(i, i + 1));
            if (i % 2 == 0) {
                newValue = 2 * num;
                if (newValue > 9) {
                    newValue -= 9;
                }
                sum += newValue;
            } else {
                sum += num;
            }

        }
        boolean isSumAMultipleOfTen = sum % 10 == 0;

        return isSumAMultipleOfTen ? 0 :  (10 - (sum % 10)) % 10;
    }

    public static boolean isOpeningBalanceEnough(BigDecimal openingAmount) {
        return ConstantValues.MINIMUM_BALANCE.compareTo(openingAmount) < 0;
    }

    public static BigDecimal getWithholdingTax(BigDecimal grossAmount) {
        return grossAmount
                .multiply(ConstantValues.WITHHOLDING_TAX);
    }
}
