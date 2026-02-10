package com.pattisian.zetta.bank_backend.accounts.dto;

import com.pattisian.zetta.bank_backend.accounts.enums.AccountType;
import com.pattisian.zetta.bank_backend.accounts.enums.BalanceSource;
import com.pattisian.zetta.bank_backend.common.enums.Currency;
import com.pattisian.zetta.bank_backend.common.exception.InsufficientBalanceException;
import com.pattisian.zetta.bank_backend.common.helpers.Helper;
import com.pattisian.zetta.bank_backend.users.dto.UserAddressDetails;
import com.pattisian.zetta.bank_backend.users.dto.UserCreationDetails;

import java.math.BigDecimal;

public class OpenAccountRequestDTO {

    private AccountType accountType;
    private BigDecimal availableBalance;
    private Currency currency;
    private UserCreationDetails userCreationDetails;
    private UserAddressDetails userAddressDetails;
    private BalanceSource balanceSource;

    public OpenAccountRequestDTO(BigDecimal availableBalance, Currency currency, UserCreationDetails userCreationDetails, UserAddressDetails userAddressDetails, BalanceSource balanceSource) {

        if (!Helper.isOpeningBalanceEnough(availableBalance)) {
            throw new InsufficientBalanceException("Must deposit at least Php2000 to open an account.");
        }
        this.accountType = AccountType.SAVINGS;
        this.availableBalance = availableBalance;
        this.currency = currency;
        this.userCreationDetails = userCreationDetails;
        this.userAddressDetails = userAddressDetails;
        this.balanceSource = balanceSource;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public UserCreationDetails getUserCreationRequestDetails() {
        return userCreationDetails;
    }

    public void setUserCreationRequestDetails(UserCreationDetails userCreationDetails) {
        this.userCreationDetails = userCreationDetails;
    }

    public UserAddressDetails getUserAddressRequestDetails() {
        return userAddressDetails;
    }

    public void setUserAddressRequestDetails(UserAddressDetails userAddressDetails) {
        this.userAddressDetails = userAddressDetails;
    }

    public BalanceSource getBalanceSource() {
        return balanceSource;
    }

    public void setBalanceSource(BalanceSource balanceSource) {
        this.balanceSource = balanceSource;
    }
}
