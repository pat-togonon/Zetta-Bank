package com.pattisian.zetta.bank_backend.bankcards.service.impl;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.bankcards.dto.ReplaceBankCardRequestDTO;
import com.pattisian.zetta.bank_backend.bankcards.dto.UpdateBankCardRequestDTO;
import com.pattisian.zetta.bank_backend.bankcards.entity.BankCard;
import com.pattisian.zetta.bank_backend.bankcards.enums.Status;
import com.pattisian.zetta.bank_backend.bankcards.repository.BankCardRepository;
import com.pattisian.zetta.bank_backend.bankcards.service.BankCardAccountService;
import com.pattisian.zetta.bank_backend.bankcards.service.BankCardService;
import com.pattisian.zetta.bank_backend.common.ConstantValues;
import com.pattisian.zetta.bank_backend.common.helpers.SecurityUtil;
import com.pattisian.zetta.bank_backend.security.context.AuthenticatedUserProvider;
import com.pattisian.zetta.bank_backend.users.entity.User;
import com.pattisian.zetta.bank_backend.users.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankCardServiceImpl implements BankCardService {

    private final BankCardRepository bankCardRepository;
    private final UserService userService;
    private final BankCardAccountService bankCardAccountService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public BankCardServiceImpl(BankCardRepository bankCardRepository, UserService userService, BankCardAccountService bankCardAccountService, AuthenticatedUserProvider authenticatedUserProvider) {
        this.bankCardRepository = bankCardRepository;
        this.userService = userService;
        this.bankCardAccountService = bankCardAccountService;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    /* for admin - with pagination
    @Override
    public List<BankCard> getAllBankCards() {
        return bankCardRepository.findAll();
    }

 */

    @Override
    public BankCard getBankCardById(Long id) {
        User user = authenticatedUserProvider.getAuthenticatedUser();
        return bankCardRepository.getBankCardToUpdateById(user, id)
                .orElseThrow(() -> new EntityNotFoundException("Bank card with an id " + id + " not found."));
    }

    @Override
    public List<BankCard> getAllActiveUserBankCards() {
        User user = authenticatedUserProvider.getAuthenticatedUser();
        return bankCardRepository.getAllBankCardsByUser(user, Status.ACTIVE);
    }

    @Transactional
    @Override
    public BankCard updateBankCard(Long id, UpdateBankCardRequestDTO request) {
        BankCard bankCard = this.getBankCardById(id);
        if (request.getStatus() != null) {
            bankCard.setStatus(request.getStatus());
        }

        if (request.getPrimaryAccountId() != null) {
            User user = authenticatedUserProvider.getAuthenticatedUser();
            Account validAccount = bankCardAccountService.getAccountById(request.getPrimaryAccountId(), user);
            bankCard.setPrimaryAccount(validAccount);
        }

        return bankCardRepository.save(bankCard);
    }

    @Transactional
    @Override
    public BankCard replaceBankCard(ReplaceBankCardRequestDTO request) {
        User user = authenticatedUserProvider.getAuthenticatedUser();
        Account validAccount = bankCardAccountService.getAccountById(request.getPrimaryAccountId(), user);
        Optional<BankCard> previousBankCard = bankCardRepository.getBankCardByUserToDeactivate(user);

        if (previousBankCard.isPresent()) {
            BankCard prevCard = previousBankCard.get();
            prevCard.setStatus(Status.DEACTIVATED);
            bankCardRepository.save(prevCard);
        }

        // validAccount.deductAmountFromBalance(); - deduct 200 - but account can be different. Can also be cash so check first
        // create a new outgoing transaction to reflect this 200

        BankCard replacementCardToSave = new BankCard(validAccount, user);

        return bankCardRepository.save(replacementCardToSave);
    }

}
