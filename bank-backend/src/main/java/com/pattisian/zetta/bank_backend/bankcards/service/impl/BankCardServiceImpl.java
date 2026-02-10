package com.pattisian.zetta.bank_backend.bankcards.service.impl;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.bankcards.dto.UpdateBankCardRequestDTO;
import com.pattisian.zetta.bank_backend.bankcards.entity.BankCard;
import com.pattisian.zetta.bank_backend.bankcards.enums.Status;
import com.pattisian.zetta.bank_backend.bankcards.repository.BankCardRepository;
import com.pattisian.zetta.bank_backend.bankcards.service.BankCardAccountService;
import com.pattisian.zetta.bank_backend.bankcards.service.BankCardService;
import com.pattisian.zetta.bank_backend.common.helpers.SecurityUtil;
import com.pattisian.zetta.bank_backend.users.entity.User;
import com.pattisian.zetta.bank_backend.users.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankCardServiceImpl implements BankCardService {

    private final BankCardRepository bankCardRepository;
    private final UserService userService;
    private final BankCardAccountService bankCardAccountService;

    public BankCardServiceImpl(BankCardRepository bankCardRepository, UserService userService, BankCardAccountService bankCardAccountService) {
        this.bankCardRepository = bankCardRepository;
        this.userService = userService;
        this.bankCardAccountService = bankCardAccountService;
    }

/* for admin - with pagination
    @Override
    public List<BankCard> getAllBankCards() {
        return bankCardRepository.findAll();
    }

 */

    @Override
    public BankCard getBankCardById(Long id) {
        User user = this.extractLoggedInUser();
        return bankCardRepository.getBankCardToUpdateById(user, id)
                .orElseThrow(() -> new EntityNotFoundException("Bank card with an id " + id + " not found."));
    }

    @Override
    public List<BankCard> getAllActiveUserBankCards() {
        User user = this.extractLoggedInUser();
        return bankCardRepository.getAllBankCardsByUser(user, Status.ACTIVE);
    }

    @Override
    public BankCard updateBankCard(Long id, UpdateBankCardRequestDTO request) {
        BankCard bankCard = this.getBankCardById(id);
        if (request.getStatus() != null) {
            bankCard.setStatus(request.getStatus());
        }

        if (request.getPrimaryAccountId() != null) {
            User user = this.extractLoggedInUser();
            Account validAccount = bankCardAccountService.getAccountById(request.getPrimaryAccountId(), user);
            bankCard.setPrimaryAccount(validAccount);
        }

        return bankCardRepository.save(bankCard);
    }

    // This one repeats. Try as AOP
    private User extractLoggedInUser() {
        String username = SecurityUtil.getLoggedInUsername();
        return userService.getUserByUsername(username);
    }


}
