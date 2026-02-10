package com.pattisian.zetta.bank_backend.accounts.service.impl;

import com.pattisian.zetta.bank_backend.accountSettings.entity.AccountSetting;
import com.pattisian.zetta.bank_backend.accountSettings.service.AccountSettingService;
import com.pattisian.zetta.bank_backend.accounts.dto.NewAccountRequestDTO;
import com.pattisian.zetta.bank_backend.accounts.dto.OpenAccountRequestDTO;
import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.accounts.enums.AccountType;
import com.pattisian.zetta.bank_backend.accounts.repository.AccountRepository;
import com.pattisian.zetta.bank_backend.accounts.service.AccountService;
import com.pattisian.zetta.bank_backend.bankcards.entity.BankCard;
import com.pattisian.zetta.bank_backend.bankcards.service.BankCardService;
import com.pattisian.zetta.bank_backend.common.ConstantValues;
import com.pattisian.zetta.bank_backend.common.enums.Currency;
import com.pattisian.zetta.bank_backend.common.exception.MaximumAccountReachedException;
import com.pattisian.zetta.bank_backend.common.helpers.SecurityUtil;
import com.pattisian.zetta.bank_backend.users.entity.Address;
import com.pattisian.zetta.bank_backend.users.entity.User;
import com.pattisian.zetta.bank_backend.users.service.AddressService;
import com.pattisian.zetta.bank_backend.users.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        Account savedAccount = saveAccount(savedUser, request.getAccountType(), request.getAvailableBalance(), request.getCurrency());
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

    private Account saveAccount(User user, AccountType accountType, BigDecimal availableBalance, Currency currency) {
        Account accountToSave = new Account(accountType, availableBalance, user, currency);
        return accountRepository.save(accountToSave);
    }

    private BankCard createBankCard(Account account) {
        return bankCardService.createNewBankCard(account);
    }

    private AccountSetting saveAccountSetting(Account account) {
        return accountSettingService.saveAccountSetting(account);
    }

    public List<Account> getAllAccountsByUser() {
        String username = SecurityUtil.getLoggedInUsername();
        Long userId = userService.getUserByUsername(username).getId();
        return accountRepository.getAllAccountsByUserId(userId);
    }

    @Override
    public Account addNewAccount(NewAccountRequestDTO request) {
        String username = SecurityUtil.getLoggedInUsername();
        User user = userService.getUserByUsername(username);
        Long userId = user.getId();

        int accountCount = accountRepository.getUserAccountTotalByAccountType(user, request.getAccountType());
        if (accountCount == ConstantValues.MAX_NUMBER_OF_ACCOUNTS) {
            throw new MaximumAccountReachedException("You have reached the maximum number of " + request.getAccountType().toString().toLowerCase() + " account allowed.");
        }
        Account accountSaved = this.saveAccount(user, request.getAccountType(), request.getAvailableBalance(), request.getCurrency());
        this.saveAccountSetting(accountSaved);

        return accountSaved;
    }


}
