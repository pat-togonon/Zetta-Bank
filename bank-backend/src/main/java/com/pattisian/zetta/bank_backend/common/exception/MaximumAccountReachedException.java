package com.pattisian.zetta.bank_backend.common.exception;

public class MaximumAccountReachedException extends RuntimeException {
    public MaximumAccountReachedException(String message) {
        super(message);
    }
}
