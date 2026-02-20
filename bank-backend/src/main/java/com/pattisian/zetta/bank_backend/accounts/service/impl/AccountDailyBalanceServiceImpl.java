package com.pattisian.zetta.bank_backend.accounts.service.impl;

import com.pattisian.zetta.bank_backend.accounts.repository.AccountDailyBalanceRepository;
import com.pattisian.zetta.bank_backend.accounts.service.AccountDailyBalanceService;
import org.springframework.stereotype.Service;

@Service
public class AccountDailyBalanceServiceImpl implements AccountDailyBalanceService {

    private final AccountDailyBalanceRepository accountDailyBalanceRepository;

    public AccountDailyBalanceServiceImpl(AccountDailyBalanceRepository accountDailyBalanceRepository) {
        this.accountDailyBalanceRepository = accountDailyBalanceRepository;
    }


}
