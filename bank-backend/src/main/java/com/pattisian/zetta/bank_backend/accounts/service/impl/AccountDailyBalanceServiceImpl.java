package com.pattisian.zetta.bank_backend.accounts.service.impl;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.accounts.entity.AccountDailyBalance;
import com.pattisian.zetta.bank_backend.accounts.repository.AccountDailyBalanceRepository;
import com.pattisian.zetta.bank_backend.accounts.repository.AccountRepository;
import com.pattisian.zetta.bank_backend.accounts.service.AccountDailyBalanceService;
import com.pattisian.zetta.bank_backend.common.ConstantValues;
import com.pattisian.zetta.bank_backend.common.helpers.Helper;
import jakarta.transaction.Transactional;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
                .map(ac -> new AccountDailyBalance(ac, ac.getAvailableBalance()))
                .toList();

        accountDailyBalanceRepository.saveAll(adbOfAllActiveAccounts);
    }

    @Transactional
    @Retryable(
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000)
    )
    @Override
    public void processMonthlySavingsInterest() {

        LocalDate today = LocalDate.now(ConstantValues.BANK_ZONE);
        YearMonth prevMonth = YearMonth.from(today).minusMonths(1);
        LocalDate startDate = prevMonth.atDay(1);
        LocalDate endDate = prevMonth.atEndOfMonth();

        List<Account> allActiveSavingsAccount = accountRepository.getAllActiveSavingsAccounts()
                .stream()
                .filter(ac -> ac.getLastInterestCreditedMonth() == null || !ac.getLastInterestCreditedMonth().equals(prevMonth))
                .toList();

        for (Account ac : allActiveSavingsAccount) {
            BigDecimal sumOfMonthlyAdb = accountDailyBalanceRepository.getSumOfClosingBalances(ac, startDate, endDate);

            BigDecimal grossInterestEarned = sumOfMonthlyAdb
                    .multiply(ac.getInterestRatePerAnnum())
                    .divide(BigDecimal.valueOf(ConstantValues.DAYS_IN_A_YEAR), 8, RoundingMode.HALF_EVEN);

            grossInterestEarned = grossInterestEarned.setScale(2, RoundingMode.HALF_EVEN);

            BigDecimal netInterestEarned = grossInterestEarned
                    .subtract(Helper.getWithholdingTax(grossInterestEarned));

            ac.addAmountToBalance(netInterestEarned);
            ac.setLastInterestCreditedMonth(prevMonth);
            // create transaction for this
        }

        accountRepository.saveAll(allActiveSavingsAccount);
    }


}
