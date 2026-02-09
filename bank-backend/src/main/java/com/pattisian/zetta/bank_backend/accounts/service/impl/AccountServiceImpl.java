package com.pattisian.zetta.bank_backend.accounts.service.impl;

import com.pattisian.zetta.bank_backend.accountSettings.entity.AccountSetting;
import com.pattisian.zetta.bank_backend.accountSettings.service.AccountSettingService;
import com.pattisian.zetta.bank_backend.accounts.dto.OpenAccountRequestDTO;
import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.accounts.repository.AccountRepository;
import com.pattisian.zetta.bank_backend.accounts.service.AccountService;
import com.pattisian.zetta.bank_backend.bankcards.entity.BankCard;
import com.pattisian.zetta.bank_backend.bankcards.service.BankCardService;
import com.pattisian.zetta.bank_backend.users.entity.Address;
import com.pattisian.zetta.bank_backend.users.entity.User;
import com.pattisian.zetta.bank_backend.users.service.AddressService;
import com.pattisian.zetta.bank_backend.users.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserService userService;
    private final AddressService addressService;
    private final BankCardService bankCardService;
    private final AccountSettingService accountSettingService;

    public AccountServiceImpl(AccountRepository accountRepository, UserService userService, AddressService addressService, BankCardService bankCardService, AccountSettingService accountSettingService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.addressService = addressService;
        this.bankCardService = bankCardService;
        this.accountSettingService = accountSettingService;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Transactional
    @Override
    public Account openAccount(OpenAccountRequestDTO request) {
        User savedUser = this.saveUser(request);
        Address savedAddress = saveAddress(savedUser, request);
        Account savedAccount = saveAccount(savedUser, request);
        this.createBankCard(savedAccount);
        this.saveAccountSetting(savedAccount);

        return savedAccount;
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account with id " + id + " not found."));

    }

    private User saveUser(OpenAccountRequestDTO request) {
        User userToSave = new User(request.getUserCreationRequestDTO().getFirstName(), request.getUserCreationRequestDTO().getMiddleName(), request.getUserCreationRequestDTO().getLastName(), request.getUserCreationRequestDTO().getDateOfBirth(), request.getUserCreationRequestDTO().getEmail(), request.getUserCreationRequestDTO().getMobile(), request.getUserCreationRequestDTO().getUsername(), request.getUserCreationRequestDTO().getPassword());
        return userService.createNewUser(userToSave);
    }

    private Address saveAddress(User user, OpenAccountRequestDTO request) {
        Address addressToSave = new Address(user, request.getUserAddressRequestDTO().getHouseNumber(), request.getUserAddressRequestDTO().getStreet(), request.getUserAddressRequestDTO().getCity(), request.getUserAddressRequestDTO().getZipCode());
        return addressService.saveAddress(addressToSave);
    }

    private Account saveAccount(User user, OpenAccountRequestDTO request) {
        Account accountToSave = new Account(request.getAccountType(), request.getAvailableBalance(), user, request.getCurrency());
        return accountRepository.save(accountToSave);
    }

    private BankCard createBankCard(Account account) {
        return bankCardService.createNewBankCard(account);
    }

    private AccountSetting saveAccountSetting(Account account) {
        return accountSettingService.saveAccountSetting(account);
    }

    public List<Account> getAllAccountsByUserId(Long id) {
        return accountRepository.getAllAccountsByUserId(id);
    }
}
