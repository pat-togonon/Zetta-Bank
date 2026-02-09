package com.pattisian.zetta.bank_backend.bankcards.controller;

import com.pattisian.zetta.bank_backend.bankcards.entity.BankCard;
import com.pattisian.zetta.bank_backend.bankcards.service.BankCardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bank-cards")
public class BankCardController {

    private final BankCardService bankCardService;

    public BankCardController(BankCardService bankCardService) {
        this.bankCardService = bankCardService;
    }

    // for admin only - For AOP/Security checking
    @GetMapping()
    public List<BankCard> getAllBankCards() {
        return bankCardService.getAllBankCards();
    }

    @GetMapping("/{id}")
    public BankCard getBankCardById(@PathVariable Long id) {
        return bankCardService.getBankCardById(id);
    }
}
