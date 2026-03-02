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
public class IncomingWireTransfer extends Transaction {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiving_account_id", nullable = false)
    @NotNull
    private Account receivingAccount;

    @Column(name = "wire_transfer_source_account_number", nullable = false)
    @NotBlank
    private String wireTransferSourceAccountNumber;

    @Column(name = "wire_transfer_source_account_name", nullable = false)
    @NotBlank
    private String wireTransferSourceAccountName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wire_transfer_source_bank_id", nullable = false)
    @NotNull
    private Bank wireTransferSourceBank;

    @Column(name = "wire_transfer_source_reference_number", nullable = false)
    @NotBlank
    private String wireTransferSourceReferenceNumber;


    public IncomingWireTransfer() {
    }

    public IncomingWireTransfer(User user, BigDecimal amount, BigDecimal balanceAfterTransaction, Account receivingAccount, String wireTransferSourceAccountNumber, String wireTransferSourceAccountName, Bank wireTransferSourceBank, String wireTransferSourceReferenceNumber) {
        super(TransactionType.INCOMING_WIRE, user, amount, receivingAccount.getAvailableBalance(), balanceAfterTransaction);
        this.receivingAccount = receivingAccount;
        this.wireTransferSourceAccountNumber = wireTransferSourceAccountNumber;
        this.wireTransferSourceAccountName = wireTransferSourceAccountName;
        this.wireTransferSourceBank = wireTransferSourceBank;
        this.wireTransferSourceReferenceNumber = wireTransferSourceReferenceNumber;
    }

    public Account getReceivingAccount() {
        return receivingAccount;
    }

    public void setReceivingAccount(Account receivingAccount) {
        this.receivingAccount = receivingAccount;
    }

    public String getWireTransferSourceAccountNumber() {
        return wireTransferSourceAccountNumber;
    }

    public void setWireTransferSourceAccountNumber(String wireTransferSourceAccountNumber) {
        this.wireTransferSourceAccountNumber = wireTransferSourceAccountNumber;
    }

    public String getWireTransferSourceAccountName() {
        return wireTransferSourceAccountName;
    }

    public void setWireTransferSourceAccountName(String wireTransferSourceAccountName) {
        this.wireTransferSourceAccountName = wireTransferSourceAccountName;
    }

    public Bank getWireTransferSourceBank() {
        return wireTransferSourceBank;
    }

    public void setWireTransferSourceBank(Bank wireTransferSourceBank) {
        this.wireTransferSourceBank = wireTransferSourceBank;
    }

    public String getWireTransferSourceReferenceNumber() {
        return wireTransferSourceReferenceNumber;
    }

    public void setWireTransferSourceReferenceNumber(String wireTransferSourceReferenceNumber) {
        this.wireTransferSourceReferenceNumber = wireTransferSourceReferenceNumber;
    }
}
