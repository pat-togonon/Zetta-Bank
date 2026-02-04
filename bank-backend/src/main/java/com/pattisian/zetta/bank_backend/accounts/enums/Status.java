package com.pattisian.zetta.bank_backend.accounts.enums;

public enum Status {
    ACTIVE, DORMANT, BLOCKED, CLOSED;
}

// add check logics for dormant
// add post request for closed (account holder + admin) and blocked (admin-only)