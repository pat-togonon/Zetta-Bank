package com.pattisian.zetta.bank_backend.common.exception;

public class TimeDepositNotActiveException extends RuntimeException {
    public TimeDepositNotActiveException(String message) {
        super(message);
    }
}
