package com.pattisian.zetta.bank_backend.accounts.scheduler;

import com.pattisian.zetta.bank_backend.accounts.service.AccountDailyBalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AccountMonthlyInterestCreditScheduler {
    private final AccountDailyBalanceService accountDailyBalanceService;

    public AccountMonthlyInterestCreditScheduler(AccountDailyBalanceService accountDailyBalanceService) {
        this.accountDailyBalanceService = accountDailyBalanceService;
    }

    @Scheduled(cron = "0 5 0 1 * *", zone = "Asia/Manila")
    public void creditMonthlySavingsAccountInterestEarned() {
        accountDailyBalanceService.processMonthlySavingsInterest();
    }
}
