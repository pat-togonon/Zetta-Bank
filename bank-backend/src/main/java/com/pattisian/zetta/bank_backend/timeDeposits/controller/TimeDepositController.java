package com.pattisian.zetta.bank_backend.timeDeposits.controller;

import com.pattisian.zetta.bank_backend.timeDeposits.dto.NewTimeDepositRequestDTO;
import com.pattisian.zetta.bank_backend.timeDeposits.entity.TimeDeposit;
import com.pattisian.zetta.bank_backend.timeDeposits.service.TimeDepositService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-deposits")
public class TimeDepositController {
    private final TimeDepositService timeDepositService;

    public TimeDepositController(TimeDepositService timeDepositService) {
        this.timeDepositService = timeDepositService;
    }

    @GetMapping("/all")
    public List<TimeDeposit> getAllTimeDeposits() {
        return timeDepositService.getAllTimeDeposits();
    }

    @GetMapping("/{id}")
    public TimeDeposit getTimeDepositById(@PathVariable Long id) {
        return timeDepositService.getTimeDepositById(id);
    }

    @PutMapping("/{id}")
    public TimeDeposit preTerminateTimeDeposit(@PathVariable Long id) {
        return timeDepositService.preTerminate(id);
    }

    @PostMapping()
    public TimeDeposit openTimeDeposit(@RequestBody NewTimeDepositRequestDTO request) {
        return timeDepositService.openTimeDeposit(request);
    }

}
