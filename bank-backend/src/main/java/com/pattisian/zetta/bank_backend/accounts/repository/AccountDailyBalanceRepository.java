package com.pattisian.zetta.bank_backend.accounts.repository;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.accounts.entity.AccountDailyBalance;
import com.pattisian.zetta.bank_backend.accounts.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountDailyBalanceRepository extends JpaRepository<AccountDailyBalance, Long> {

    Optional<AccountDailyBalance> findByAccountAndDate(Account account, LocalDate date);

    @Query("SELECT COALESCE(SUM(a.closingBalance), 0) FROM AccountDailyBalance a WHERE a.account = :account AND a.date BETWEEN :startDate AND :endDate")
    BigDecimal getSumOfClosingBalances(@Param("account") Account account, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


}
