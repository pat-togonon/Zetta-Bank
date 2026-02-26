package com.pattisian.zetta.bank_backend.transactions.service;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.users.entity.User;

import java.util.Optional;

public interface AccountTransactionService {

    Optional<Account> getUserAccount(Account account);



}
