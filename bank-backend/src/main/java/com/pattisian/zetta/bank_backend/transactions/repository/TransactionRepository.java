package com.pattisian.zetta.bank_backend.transactions.repository;

import com.pattisian.zetta.bank_backend.timeDeposits.entity.TimeDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TimeDeposit, Long> {


}
