package com.pattisian.zetta.bank_backend.accounts.repository;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.accounts.entity.AccountDailyBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountDailyBalanceRepository extends JpaRepository<AccountDailyBalance, Long> {

    Optional<AccountDailyBalance> findByAccountAndDate(Account account, LocalDate date);


}
