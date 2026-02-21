package com.pattisian.zetta.bank_backend.accounts.service;

public interface AccountDailyBalanceService {

    void saveAccountDailyBalanceSnapshot();

    void processMonthlySavingsInterest();
}
