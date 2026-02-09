package com.pattisian.zetta.bank_backend.accountSettings.service;

import com.pattisian.zetta.bank_backend.accountSettings.entity.AccountSetting;
import com.pattisian.zetta.bank_backend.accounts.entity.Account;

public interface AccountSettingService {

    public AccountSetting saveAccountSetting(Account account);
}
