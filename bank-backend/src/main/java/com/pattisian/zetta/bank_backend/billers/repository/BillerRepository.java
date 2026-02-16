package com.pattisian.zetta.bank_backend.billers.repository;

import com.pattisian.zetta.bank_backend.billers.entity.Biller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillerRepository extends JpaRepository<Biller, Long> {
}
