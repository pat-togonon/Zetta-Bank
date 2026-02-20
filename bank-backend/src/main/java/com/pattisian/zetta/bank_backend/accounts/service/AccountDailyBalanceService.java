package com.pattisian.zetta.bank_backend.accounts.service;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.accounts.entity.AccountDailyBalance;

import java.time.LocalDate;
import java.util.Optional;

public interface AccountDailyBalanceService {

    void saveAccountDailyBalanceSnapshot();
}
