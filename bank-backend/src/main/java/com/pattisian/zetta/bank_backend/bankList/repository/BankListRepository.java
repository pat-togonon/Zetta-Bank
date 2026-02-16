package com.pattisian.zetta.bank_backend.bankList.repository;

import com.pattisian.zetta.bank_backend.bankList.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankListRepository extends JpaRepository<Bank, Long> {
}
