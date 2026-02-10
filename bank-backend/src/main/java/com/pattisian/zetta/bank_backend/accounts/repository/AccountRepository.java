package com.pattisian.zetta.bank_backend.accounts.repository;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.accounts.enums.AccountType;
import com.pattisian.zetta.bank_backend.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


    public List<Account> getAllAccountsByUser(User user);

    @Query("SELECT COUNT(*) FROM Account a WHERE a.user = :user AND a.accountType = :accountType")
    public int getUserAccountTotalByAccountType(@Param("user") User user, @Param("accountType") AccountType accountType);

    @Query("SELECT a FROM Account a WHERE a.user = :user AND a.id = :id")
    public Optional<Account> getAccountById(@Param("user") User user, @Param("id") Long id);
}
