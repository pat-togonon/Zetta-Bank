package com.pattisian.zetta.bank_backend.timeDeposits.service;

import com.pattisian.zetta.bank_backend.timeDeposits.dto.NewTimeDepositRequestDTO;
import com.pattisian.zetta.bank_backend.timeDeposits.entity.TimeDeposit;

import java.util.List;

public interface TimeDepositService {

     /*
    openTimeDeposit - CREATE/POST/SAVE
    preTerminate - UPDATE
    getAllTimeDeposits - for admin + paginated
    getTimeDepositById - for user (user id + time depo id) + admin
    getAllTimeDepositByUserId - for user

    Separate:
    rollOverTimeDeposit - Scheduled (private method) with taxes - UPDATE
    terminateTimeDeposit - Scheduled (private method) with taxes - UPDATE
     */

    TimeDeposit openTimeDeposit(NewTimeDepositRequestDTO request);
    TimeDeposit preTerminate(Long id);
    List<TimeDeposit> getAllTimeDeposits();
    TimeDeposit getTimeDepositById(Long id);
    void processMatureTimeDepositsAndRollOvers();

    // update td - change payout account, set autoRenew = false but only when autoRenew is initially true. Canot update anymore when TD is matured or pre-terminated



}
