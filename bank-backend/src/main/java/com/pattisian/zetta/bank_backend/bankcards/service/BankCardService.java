package com.pattisian.zetta.bank_backend.bankcards.service;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.bankcards.entity.BankCard;

public interface BankCardService {

    public BankCard createNewBankCard(Account account);
}
