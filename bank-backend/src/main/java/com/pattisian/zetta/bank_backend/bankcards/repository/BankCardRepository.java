package com.pattisian.zetta.bank_backend.bankcards.repository;

import com.pattisian.zetta.bank_backend.bankcards.entity.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCardRepository extends JpaRepository<BankCard, Long> {
}
