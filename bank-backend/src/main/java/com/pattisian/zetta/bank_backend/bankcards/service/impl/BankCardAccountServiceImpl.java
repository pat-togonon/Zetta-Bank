package com.pattisian.zetta.bank_backend.bankcards.service.impl;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.accounts.repository.AccountRepository;
import com.pattisian.zetta.bank_backend.accounts.service.AccountService;
import com.pattisian.zetta.bank_backend.bankcards.entity.BankCard;
import com.pattisian.zetta.bank_backend.bankcards.repository.BankCardRepository;
import com.pattisian.zetta.bank_backend.bankcards.service.BankCardAccountService;
import com.pattisian.zetta.bank_backend.bankcards.service.BankCardService;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BankCardAccountServiceImpl implements BankCardAccountService {

    private final BankCardRepository bankCardRepository;
    private final AccountRepository accountRepository;

    public BankCardAccountServiceImpl(BankCardRepository bankCardRepository, AccountRepository accountRepository) {
        this.bankCardRepository = bankCardRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public BankCard createNewBankCard(Account account, User user) {
        BankCard bankCardToCreate = new BankCard(account, user);
        return bankCardRepository.save(bankCardToCreate);
    }

    @Override
    public BankCard replaceCard(Long primaryAccountId) {

        return null;
    }

    @Override
    public Account getAccountById(Long primaryAccountId, User user) {
        return accountRepository.getAccountById(primaryAccountId, user)
                .orElseThrow(() -> new EntityNotFoundException("Account with an id " + primaryAccountId + " is not found."));
    }
}
