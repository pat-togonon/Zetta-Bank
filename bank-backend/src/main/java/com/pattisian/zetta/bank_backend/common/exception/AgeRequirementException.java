package com.pattisian.zetta.bank_backend.common.exception;

public class AgeRequirementException extends RuntimeException {

    public AgeRequirementException(int legalAge) {
        super("You must be at least " + legalAge + " years old to open an account.");
    }
}
