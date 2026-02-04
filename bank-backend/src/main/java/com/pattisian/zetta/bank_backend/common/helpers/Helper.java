package com.pattisian.zetta.bank_backend.common.helpers;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

public class Helper {


    public static String generateAccountNumber(Long id, String accountTypeCode, String bankCode, String branchCode, Instant dateOpened) {
        String sequence = String.format("%08d", id);
        int yearOpened = extractYear(dateOpened);
        String accountNumber = String.format("%s-%s-%s-%s-%s", accountTypeCode, bankCode, branchCode, yearOpened, sequence);

        return accountNumber;
    }

    public static int extractYear(Instant date) {
        ZonedDateTime zdt = date.atZone(ZoneId.of("Asia/Manila"));
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
}
