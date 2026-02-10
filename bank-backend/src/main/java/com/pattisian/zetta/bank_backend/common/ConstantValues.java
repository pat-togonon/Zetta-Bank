package com.pattisian.zetta.bank_backend.common;

import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.ZoneId;

public final class ConstantValues {

    //Can't create instance from outside
    private ConstantValues() {

    }

    public static final int DAILY_WITHDRAWAL_COUNT_LIMIT = 5;

    public static final BigDecimal DAILY_WITHDRAWAL_AMOUNT_LIMIT = new BigDecimal("50000");

    public static final int DAILY_BILL_PAYMENT_COUNT_LIMIT = 5;

    public static final BigDecimal DAILY_BILL_PAYMENT_AMOUNT_LIMIT = new BigDecimal("100000");

    public static final int DAILY_EXTERNAL_FUND_TRANSFER_COUNT_LIMIT = 5;

    public static final BigDecimal DAILY_EXTERNAL_FUND_TRANSFER_AMOUNT_LIMIT = new BigDecimal("100000");

    public static final ZoneId BANK_ZONE = ZoneId.of("Asia/Manila");

    public static final BigDecimal MINIMUM_BALANCE = new BigDecimal(2000);

    public static final int MAX_NUMBER_OF_ACCOUNTS = 2;

}
