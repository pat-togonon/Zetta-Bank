package com.pattisian.zetta.bank_backend.bankcards.service;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.bankcards.dto.ReplaceBankCardRequestDTO;
import com.pattisian.zetta.bank_backend.bankcards.dto.UpdateBankCardRequestDTO;
import com.pattisian.zetta.bank_backend.bankcards.entity.BankCard;
import com.pattisian.zetta.bank_backend.users.entity.User;

import java.util.List;

public interface BankCardService {

    //public List<BankCard> getAllBankCards();
    public BankCard getBankCardById(Long id);

    List<BankCard> getAllActiveUserBankCards();

    BankCard updateBankCard(Long id, UpdateBankCardRequestDTO request);

    BankCard replaceBankCard(ReplaceBankCardRequestDTO request);
}
