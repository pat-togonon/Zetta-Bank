package com.pattisian.zetta.bank_backend.bankcards.service.impl;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.bankcards.entity.BankCard;
import com.pattisian.zetta.bank_backend.bankcards.repository.BankCardRepository;
import com.pattisian.zetta.bank_backend.bankcards.service.BankCardService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankCardServiceImpl implements BankCardService {

    private final BankCardRepository bankCardRepository;

    public BankCardServiceImpl(BankCardRepository bankCardRepository) {
        this.bankCardRepository = bankCardRepository;
    }

    @Override
    public BankCard createNewBankCard(Account account) {
        BankCard bankCardToCreate = new BankCard(account);
        return bankCardRepository.save(bankCardToCreate);
    }

    @Override
    public List<BankCard> getAllBankCards() {
        return bankCardRepository.findAll();
    }

    @Override
    public BankCard getBankCardById(Long id) {
        return bankCardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bank card with an id " + id + " not found."));
    }
}
