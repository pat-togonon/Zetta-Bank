package com.pattisian.zetta.bank_backend.timeDeposits.scheduler;

import com.pattisian.zetta.bank_backend.timeDeposits.service.TimeDepositService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimeDepositScheduler {

    private final TimeDepositService timeDepositService;
    private final Logger log = LoggerFactory.getLogger(TimeDepositScheduler.class);

    public TimeDepositScheduler(TimeDepositService timeDepositService) {
        this.timeDepositService = timeDepositService;
    }

    // every 5 mins
    @Scheduled(cron = "0 0/5 * * * *", zone = "Asia/Manila")
    public void processMatureTimeDepositsAndRollOvers() {
        this.processTimeDepositsAsync();
    }
    @Async
    public void processTimeDepositsAsync() {
        try {
            timeDepositService.processMatureTimeDepositsAndRollOvers();
        } catch (Exception e) {
            log.error("Error process time deposits: ", e);

        }
    }


}
