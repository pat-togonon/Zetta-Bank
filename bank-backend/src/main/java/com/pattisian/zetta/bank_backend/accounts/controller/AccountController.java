package com.pattisian.zetta.bank_backend.accounts.controller;

import com.pattisian.zetta.bank_backend.accounts.dto.OpenAccountRequestDTO;
import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.accounts.service.AccountService;
import com.pattisian.zetta.bank_backend.users.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @GetMapping()
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // open a new account - for new applications / non-account holder
    @PostMapping()
    public Account openAccount(@RequestBody OpenAccountRequestDTO requestDTO) {
        return accountService.openAccount(requestDTO);

    }

    // add a new account - for existing account holder
    @PostMapping("/new-account")
    public Account addNewAccount() {
        // create a request dto for this + service method
    }

    //get one account by id
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    //get all accounts of a user by their user id
    @GetMapping("/all/{id}")
    public List<Account> getAllAccountsByUserId(@PathVariable Long id) {
        return accountService.getAllAccountsByUserId(id);
    }

}
