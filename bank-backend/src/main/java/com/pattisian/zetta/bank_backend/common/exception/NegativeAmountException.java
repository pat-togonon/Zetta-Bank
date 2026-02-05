package com.pattisian.zetta.bank_backend.common.exception;

public class NegativeAmountException extends RuntimeException {
    public NegativeAmountException(String message) {
        super(message);
    }
}
