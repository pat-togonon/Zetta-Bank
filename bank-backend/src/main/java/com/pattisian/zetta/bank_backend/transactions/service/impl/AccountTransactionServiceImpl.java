package com.pattisian.zetta.bank_backend.transactions.service.impl;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.accounts.repository.AccountRepository;
import com.pattisian.zetta.bank_backend.security.context.AuthenticatedUserProvider;
import com.pattisian.zetta.bank_backend.transactions.service.AccountTransactionService;
import com.pattisian.zetta.bank_backend.users.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private final AccountRepository accountRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public AccountTransactionServiceImpl(AccountRepository accountRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.accountRepository = accountRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @Override
    public Optional<Account> getUserAccount(Account account) {
        User user = authenticatedUserProvider.getAuthenticatedUser();
        return accountRepository.getAccountById(account.getId(), user);
    }
}
