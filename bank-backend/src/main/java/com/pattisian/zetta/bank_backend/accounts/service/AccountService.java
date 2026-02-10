package com.pattisian.zetta.bank_backend.accounts.service;

import com.pattisian.zetta.bank_backend.accounts.dto.NewAccountRequestDTO;
import com.pattisian.zetta.bank_backend.accounts.dto.OpenAccountRequestDTO;
import com.pattisian.zetta.bank_backend.accounts.entity.Account;

import java.util.List;

public interface AccountService {

   // public List<Account> getAllAccounts();

    public Account openAccount(OpenAccountRequestDTO request);


    public Account getAccountById(Long id);

    public List<Account> getAllAccountsByUser();

    public Account addNewAccount(NewAccountRequestDTO request);
}
