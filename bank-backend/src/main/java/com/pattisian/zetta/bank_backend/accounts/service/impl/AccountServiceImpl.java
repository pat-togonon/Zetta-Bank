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
/* for admin - next phase + paginated
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

 */

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
        User user = this.extractLoggedInUser();

        return accountRepository.getAccountById(user, id)
                .orElseThrow(() -> new EntityNotFoundException("Your account with an id " + id + " is not found."));

    }

    private User saveUser(OpenAccountRequestDTO request) {
        User userToSave = new User(request.getUserCreationRequestDetails().getFirstName(), request.getUserCreationRequestDetails().getMiddleName(), request.getUserCreationRequestDetails().getLastName(), request.getUserCreationRequestDetails().getDateOfBirth(), request.getUserCreationRequestDetails().getEmail(), request.getUserCreationRequestDetails().getMobile(), request.getUserCreationRequestDetails().getUsername(), request.getUserCreationRequestDetails().getPassword());
        return userService.createNewUser(userToSave);
    }

    private Address saveAddress(User user, OpenAccountRequestDTO request) {
        Address addressToSave = new Address(user, request.getUserAddressRequestDetails().getHouseNumber(), request.getUserAddressRequestDetails().getStreet(), request.getUserAddressRequestDetails().getCity(), request.getUserAddressRequestDetails().getZipCode());
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
        User user = this.extractLoggedInUser();
        return accountRepository.getAllAccountsByUser(user);
    }

    @Override
    public Account addNewAccount(NewAccountRequestDTO request) {
        User user = this.extractLoggedInUser();
        Long userId = user.getId();

        int accountCount = accountRepository.getUserAccountTotalByAccountType(user, request.getAccountType());
        if (accountCount == ConstantValues.MAX_NUMBER_OF_ACCOUNTS) {
            throw new MaximumAccountReachedException("You have reached the maximum number of " + request.getAccountType().toString().toLowerCase() + " account allowed.");
        }
        // if balance source is cash, as is. But if account, create outgoing transaction for that account
        Account accountSaved = this.saveAccount(user, request.getAccountType(), request.getAvailableBalance(), request.getCurrency());
        this.saveAccountSetting(accountSaved);

        return accountSaved;
    }

    private User extractLoggedInUser() {
        String username = SecurityUtil.getLoggedInUsername();
        return userService.getUserByUsername(username);
    }


}
