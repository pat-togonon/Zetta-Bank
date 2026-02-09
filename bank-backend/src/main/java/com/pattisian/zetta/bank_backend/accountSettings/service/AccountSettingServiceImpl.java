package com.pattisian.zetta.bank_backend.accountSettings.service;

import com.pattisian.zetta.bank_backend.accountSettings.entity.AccountSetting;
import com.pattisian.zetta.bank_backend.accountSettings.repository.AccountSettingRepository;
import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountSettingServiceImpl implements AccountSettingService {

    private final AccountSettingRepository accountSettingRepository;

    public AccountSettingServiceImpl(AccountSettingRepository accountSettingRepository) {
        this.accountSettingRepository = accountSettingRepository;
    }


    @Override
    public AccountSetting saveAccountSetting(Account account) {
        AccountSetting accountSettingToSave = AccountSetting.builder(account).build();
        return accountSettingRepository.save(accountSettingToSave);
    }
}
