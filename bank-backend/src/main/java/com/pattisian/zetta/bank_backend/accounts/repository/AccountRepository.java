package com.pattisian.zetta.bank_backend.accounts.repository;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.accounts.enums.AccountType;
import com.pattisian.zetta.bank_backend.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


    public List<Account> getAllAccountsByUserId(Long userId);

    @Query("SELECT COUNT(*) FROM Account a WHERE a.user = :user AND a.accountType = :accountType")
    public int getUserAccountTotalByAccountType(@Param("user") User user, @Param("accountType") AccountType accountType);
}
