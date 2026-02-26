package com.pattisian.zetta.bank_backend.transactions.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.transactions.enums.TransactionType;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class BillPayment extends Transaction {

    @Column(name = "source_account_id", nullable = false)
    @NotNull
    private Long sourceAccountId;

    @Column(name = "biller_id", nullable = false)
    @NotNull
    private Long billerId;

    @Column(name = "bill_account_number", nullable = false)
    @NotBlank
    private String billAccountNumber;

    @Column(name = "bill_account_name", nullable = false)
    @NotNull
    private String billAccountName;

    public BillPayment() {
        super();
    }

    // TransactionType type, User user, BigDecimal amount, BigDecimal balanceBeforeTransaction
    public BillPayment(Long sourceAccountId, BigDecimal balanceBeforeTransaction, Long billerId, String billAccountNumber, String billAccountName, User user, BigDecimal amount) {
        super(TransactionType.BILL_PAYMENT, user, amount, balanceBeforeTransaction);

        this.sourceAccountId = sourceAccountId;
        this.billerId = billerId;
        this.billAccountNumber = billAccountNumber;
        this.billAccountName = billAccountName;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public Long getBiller() {
        return billerId;
    }

    public void setBiller(Long billerId) {
        this.billerId = billerId;
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
