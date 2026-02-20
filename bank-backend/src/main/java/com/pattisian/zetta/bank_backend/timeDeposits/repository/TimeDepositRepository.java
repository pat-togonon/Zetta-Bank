package com.pattisian.zetta.bank_backend.timeDeposits.repository;

import com.pattisian.zetta.bank_backend.timeDeposits.entity.TimeDeposit;
import com.pattisian.zetta.bank_backend.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeDepositRepository extends JpaRepository<TimeDeposit, Long> {
    /*
    openTimeDeposit - CREATE/POST/SAVE
    preTerminate - UPDATE
    getAllTimeDeposits - for admin + paginated
    getTimeDepositById - for user (user id + time depo id) + admin
    getAllTimeDepositByUserId - for user
    rollOverTimeDeposit - Scheduled (private method) with taxes - UPDATE
    terminateTimeDeposit - Scheduled (private method) with taxes - UPDATE
     */

    List<TimeDeposit> getAllTimeDepositsByUser(User user);

    @Query("SELECT t FROM TimeDeposit t WHERE t.user = :user AND t.id = :id")
    Optional<TimeDeposit> getTimeDepositById(@Param("user") User user, @Param("id") Long id);

    @Query("SELECT t FROM TimeDeposit t WHERE t.isAutoRenew = TRUE AND t.maturityDate = :maturityDate AND t.status = com.pattisian.zetta.bank_backend.timeDeposits.enums.Status.ACTIVE")
    List<TimeDeposit> getMaturedTimeDepositToAutoRenew(@Param("maturityDate") LocalDate maturityDate);

    @Query("SELECT t FROM TimeDeposit t WHERE t.maturityDate = :maturityDate AND t.status = com.pattisian.zetta.bank_backend.timeDeposits.enums.Status.ACTIVE")
    List<TimeDeposit> getMaturedTimeDeposit(@Param("maturityDate") LocalDate maturityDate);


}
