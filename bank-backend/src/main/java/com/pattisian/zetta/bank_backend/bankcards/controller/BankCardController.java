package com.pattisian.zetta.bank_backend.bankcards.controller;

import com.pattisian.zetta.bank_backend.bankcards.dto.UpdateBankCardRequestDTO;
import com.pattisian.zetta.bank_backend.bankcards.entity.BankCard;
import com.pattisian.zetta.bank_backend.bankcards.service.BankCardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank-cards")
public class BankCardController {

    private final BankCardService bankCardService;

    public BankCardController(BankCardService bankCardService) {
        this.bankCardService = bankCardService;
    }

    // for admin only - For AOP/Security checking
    /*
    @GetMapping()
    public List<BankCard> getAllBankCards() {
        return bankCardService.getAllBankCards();
    }
     */
    // For users to fetch their active bank cards
    @GetMapping("/all")
    public List<BankCard> getAllActiveUserBankCards() {
        return bankCardService.getAllActiveUserBankCards();
    }

    @GetMapping("/{id}")
    public BankCard getBankCardById(@PathVariable Long id) {
        return bankCardService.getBankCardById(id);
    }

    @PutMapping("/{id}")
    public BankCard updateBankCard(@PathVariable Long id, @RequestBody UpdateBankCardRequestDTO request) {
        return bankCardService.updateBankCard(id, request);
    }

    @PostMapping("/bank-card-replacement")
    public BankCard replaceBankCard() {
        return null;
    }


}
