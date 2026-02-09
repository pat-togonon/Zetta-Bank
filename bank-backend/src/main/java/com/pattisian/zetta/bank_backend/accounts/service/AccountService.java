package com.pattisian.zetta.bank_backend.accounts.service;

import com.pattisian.zetta.bank_backend.accounts.dto.OpenAccountRequestDTO;
import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.users.entity.User;

import java.util.List;

public interface AccountService {

    public List<Account> getAllAccounts();

    public Account openAccount(OpenAccountRequestDTO request);
}
