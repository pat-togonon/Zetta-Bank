package com.pattisian.zetta.bank_backend.bankcards.service;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.bankcards.entity.BankCard;

import java.util.List;

public interface BankCardService {

    public BankCard createNewBankCard(Account account);
    public List<BankCard> getAllBankCards();
    public BankCard getBankCardById(Long id);
}
