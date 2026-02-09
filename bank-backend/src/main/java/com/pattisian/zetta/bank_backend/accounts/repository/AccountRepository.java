package com.pattisian.zetta.bank_backend.accounts.repository;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {


    public List<Account> getAllAccountsByUserId(Long id);
}
