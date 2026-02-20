package com.pattisian.zetta.bank_backend.accounts.scheduler;

import com.pattisian.zetta.bank_backend.accounts.service.AccountDailyBalanceService;
import com.pattisian.zetta.bank_backend.timeDeposits.scheduler.TimeDepositScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AccountDailyBalanceScheduler {

    private final AccountDailyBalanceService accountDailyBalanceService;
    private final Logger log = LoggerFactory.getLogger(AccountDailyBalanceScheduler.class);

    public AccountDailyBalanceScheduler(AccountDailyBalanceService accountDailyBalanceService) {
        this.accountDailyBalanceService = accountDailyBalanceService;
    }

    @Scheduled(cron = "0 0 0/4 * * *", zone = "Asia/Manila")
    public void handleAccountDailyBalanceSnapshots() {
        this.processAccountDailyBalanceSnapshotsAsync();
    }

    @Async
    public void processAccountDailyBalanceSnapshotsAsync() {
        try {
            accountDailyBalanceService.saveAccountDailyBalanceSnapshot();
        } catch (Exception e) {
            log.error("Error savings account snapshots: ", e);
        }
    }
}
