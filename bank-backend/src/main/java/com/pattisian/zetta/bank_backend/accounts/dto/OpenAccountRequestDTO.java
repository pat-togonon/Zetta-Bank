package com.pattisian.zetta.bank_backend.accounts.dto;

import com.pattisian.zetta.bank_backend.accounts.enums.AccountType;
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

    public OpenAccountRequestDTO(BigDecimal availableBalance, Currency currency, UserCreationDetails userCreationDetails, UserAddressDetails userAddressDetails) {

        if (!Helper.isOpeningBalanceEnough(availableBalance)) {
            throw new InsufficientBalanceException("Must deposit at least Php2000 to open an account.");
        }
        this.accountType = AccountType.SAVINGS;
        this.availableBalance = availableBalance;
        this.currency = currency;
        this.userCreationDetails = userCreationDetails;
        this.userAddressDetails = userAddressDetails;
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

    public UserCreationDetails getUserCreationRequestDTO() {
        return userCreationDetails;
    }

    public void setUserCreationRequestDTO(UserCreationDetails userCreationDetails) {
        this.userCreationDetails = userCreationDetails;
    }

    public UserAddressDetails getUserAddressRequestDTO() {
        return userAddressDetails;
    }

    public void setUserAddressRequestDTO(UserAddressDetails userAddressDetails) {
        this.userAddressDetails = userAddressDetails;
    }
}
