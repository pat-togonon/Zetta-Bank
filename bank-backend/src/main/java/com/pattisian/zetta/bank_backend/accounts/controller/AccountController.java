package com.pattisian.zetta.bank_backend.accounts.controller;

import com.pattisian.zetta.bank_backend.accounts.dto.NewAccountRequestDTO;
import com.pattisian.zetta.bank_backend.accounts.dto.OpenAccountRequestDTO;
import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.accounts.service.AccountService;
import com.pattisian.zetta.bank_backend.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /* for admin - next phase
    @GetMapping()
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }
    */

    // open a new account - for new applications / non-account holder
    @PostMapping("/open-account")
    public Account openAccount(@RequestBody OpenAccountRequestDTO requestDTO) {
        return accountService.openAccount(requestDTO);

    }

    // add a new account - for existing account holder
    @PostMapping("/new-account")
    public Account addNewAccount(@RequestBody NewAccountRequestDTO request) {
        return accountService.addNewAccount(request);
    }

    //get one account by id
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    //get all accounts of a user
    @GetMapping("/all")
    public List<Account> getAllAccountsByUser() {
        return accountService.getAllAccountsByUser();
    }

    // Update account - accessible for admins only - next phase

    // Delete/close account - go to branch please

}
