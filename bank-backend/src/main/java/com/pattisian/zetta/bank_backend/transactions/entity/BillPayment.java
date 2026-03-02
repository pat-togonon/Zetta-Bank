package com.pattisian.zetta.bank_backend.transactions.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.billers.entity.Biller;
import com.pattisian.zetta.bank_backend.common.exception.InsufficientBalanceException;
import com.pattisian.zetta.bank_backend.transactions.enums.TransactionType;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class BillPayment extends Transaction {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id", nullable = false)
    @NotNull
    private Account sourceAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biller_id", nullable = false)
    @NotNull
    private Biller biller;

    @Column(name = "bill_account_number", nullable = false)
    @NotBlank
    private String billAccountNumber;

    @Column(name = "bill_account_name", nullable = false)
    @NotNull
    private String billAccountName;

    public BillPayment() {

    }

    // TransactionType type, User user, BigDecimal amount, BigDecimal balanceBeforeTransaction
    public BillPayment(Account sourceAccount, Biller biller, String billAccountNumber, String billAccountName, User user, BigDecimal amount, BigDecimal balanceAfterTransaction) {
        super(TransactionType.BILL_PAYMENT, user, amount, sourceAccount.getAvailableBalance(), balanceAfterTransaction);

        this.sourceAccount = sourceAccount;
        this.biller = biller;
        this.billAccountNumber = billAccountNumber;
        this.billAccountName = billAccountName;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Biller getBiller() {
        return biller;
    }

    public void setBiller(Biller billerId) {
        this.biller = billerId;
    }

    public String getBillAccountNumber() {
        return billAccountNumber;
    }

    public void setBillAccountNumber(String billAccountNumber) {
        this.billAccountNumber = billAccountNumber;
    }

    public String getBillAccountName() {
        return billAccountName;
    }

    public void setBillAccountName(String billAccountName) {
        this.billAccountName = billAccountName;
    }
}
