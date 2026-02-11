package com.pattisian.zetta.bank_backend.bankcards.service;


import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.bankcards.entity.BankCard;
import com.pattisian.zetta.bank_backend.users.entity.User;

public interface BankCardAccountService {

    BankCard createNewBankCard(Account account, User user);

    Account getAccountById(Long primaryAccountId, User user);
}
