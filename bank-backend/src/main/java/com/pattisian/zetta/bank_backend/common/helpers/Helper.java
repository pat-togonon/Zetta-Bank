package com.pattisian.zetta.bank_backend.common.helpers;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
}
