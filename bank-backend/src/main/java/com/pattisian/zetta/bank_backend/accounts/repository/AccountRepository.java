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

    // check locking as well
    @Query("SELECT a FROM Account a WHERE a.status = com.pattisian.zetta.bank_backend.accounts.enums.Status.ACTIVE")
    public List<Account> getAllAccounts();

    @Query("SELECT a FROM Account a WHERE a.status = com.pattisian.zetta.bank_backend.accounts.enums.Status.ACTIVE AND a.accountType = com.pattisian.zetta.bank_backend.accounts.enums.AccountType.SAVINGS")
    public List<Account> getAllActiveSavingsAccounts();

    @Query("SELECT a FROM Account a WHERE a.user = :user AND a.status = com.pattisian.zetta.bank_backend.accounts.enums.Status.ACTIVE")
    public List<Account> getAllAccountsByUser(@Param("user") User user);

    @Query("SELECT COUNT(*) FROM Account a WHERE a.user = :user AND a.accountType = :accountType AND a.status = com.pattisian.zetta.bank_backend.accounts.enums.Status.ACTIVE")
    public int getUserAccountTotalByAccountType(@Param("user") User user, @Param("accountType") AccountType accountType);


    @Query("SELECT a FROM Account a WHERE a.user = :user AND a.id = :primaryAccountId AND a.status = com.pattisian.zetta.bank_backend.accounts.enums.Status.ACTIVE")
    Optional<Account> getAccountById(@Param("primaryAccountId") Long primaryAccountId, @Param("user") User user);
}
