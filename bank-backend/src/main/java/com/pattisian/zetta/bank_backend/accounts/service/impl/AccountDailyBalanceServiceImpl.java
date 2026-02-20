package com.pattisian.zetta.bank_backend.accounts.service.impl;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.accounts.entity.AccountDailyBalance;
import com.pattisian.zetta.bank_backend.accounts.repository.AccountDailyBalanceRepository;
import com.pattisian.zetta.bank_backend.accounts.repository.AccountRepository;
import com.pattisian.zetta.bank_backend.accounts.service.AccountDailyBalanceService;
import com.pattisian.zetta.bank_backend.common.ConstantValues;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountDailyBalanceServiceImpl implements AccountDailyBalanceService {

    private final AccountDailyBalanceRepository accountDailyBalanceRepository;
    private final AccountRepository accountRepository;

    public AccountDailyBalanceServiceImpl(AccountDailyBalanceRepository accountDailyBalanceRepository, AccountRepository accountRepository) {
        this.accountDailyBalanceRepository = accountDailyBalanceRepository;
        this.accountRepository = accountRepository;
    }

    // also study number of days per month in real life banks (30 vs 31 vs 28 vs leap year - how?)

    @Transactional
    @Override
    public void saveAccountDailyBalanceSnapshot() {
        List<AccountDailyBalance> adbOfAllActiveAccounts = accountRepository.getAllAccounts()
                .stream()
                .filter(ac -> accountDailyBalanceRepository
                        .findByAccountAndDate(ac, LocalDate.now(ConstantValues.BANK_ZONE))
                        .isEmpty())
                .map(ac -> new AccountDailyBalance(ac, ac.getAvailableBalance(), ac.getAccountType()))
                .collect(Collectors.toList());

        accountDailyBalanceRepository.saveAll(adbOfAllActiveAccounts);
    }


}
