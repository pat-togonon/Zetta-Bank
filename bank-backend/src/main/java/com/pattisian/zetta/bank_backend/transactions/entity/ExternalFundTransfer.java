package com.pattisian.zetta.bank_backend.transactions.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.bankList.entity.Bank;
import com.pattisian.zetta.bank_backend.transactions.enums.TransactionType;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ExternalFundTransfer extends Transaction {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id", nullable = false)
    @NotNull
    private Account sourceAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_bank_id", nullable = false)
    @NotNull
    private Bank destinationBank;

    @Column(name = "external_destination_account_number", nullable = false)
    @NotBlank
    private String externalDestinationAccountNumber;

    @Column(name = "external_destination_account_name", nullable = false)
    @NotBlank
    private String getExternalDestinationAccountName;

    public ExternalFundTransfer() {

    }

    // TransactionType type, User user, BigDecimal amount, BigDecimal balanceBeforeTransaction
    public ExternalFundTransfer(User user, BigDecimal amount, Account sourceAccount, Bank destinationBank, String externalDestinationAccountNumber, String getExternalDestinationAccountName, BigDecimal balanceAfterTransaction) {
        super(TransactionType.EXTERNAL_FUND_TRANSFER, user, amount, sourceAccount.getAvailableBalance(), balanceAfterTransaction);

        this.sourceAccount = sourceAccount;
        this.destinationBank = destinationBank;
        this.externalDestinationAccountNumber = externalDestinationAccountNumber;
        this.getExternalDestinationAccountName = getExternalDestinationAccountName;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Bank getDestinationBank() {
        return destinationBank;
    }

    public void setDestinationBank(Bank destinationBank) {
        this.destinationBank = destinationBank;
    }

    public String getExternalDestinationAccountNumber() {
        return externalDestinationAccountNumber;
    }

    public void setExternalDestinationAccountNumber(String externalDestinationAccountNumber) {
        this.externalDestinationAccountNumber = externalDestinationAccountNumber;
    }

    public String getGetExternalDestinationAccountName() {
        return getExternalDestinationAccountName;
    }

    public void setGetExternalDestinationAccountName(String getExternalDestinationAccountName) {
        this.getExternalDestinationAccountName = getExternalDestinationAccountName;
    }
}
