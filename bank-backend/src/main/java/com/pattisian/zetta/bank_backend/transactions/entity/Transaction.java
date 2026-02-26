package com.pattisian.zetta.bank_backend.transactions.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.common.ConstantValues;
import com.pattisian.zetta.bank_backend.common.exception.InsufficientBalanceException;
import com.pattisian.zetta.bank_backend.common.helpers.Helper;
import com.pattisian.zetta.bank_backend.timeDeposits.entity.TimeDeposit;
import com.pattisian.zetta.bank_backend.transactions.enums.Status;
import com.pattisian.zetta.bank_backend.transactions.enums.TransactionType;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

// Also check out @EntityListeners(AuditingEntityListener.class)

@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "reference_number", unique = true, nullable = false)
    @NotBlank
    private String referenceNumber; // to derive from id Pat

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, precision = 18, scale = 2)
    @NotNull
    private BigDecimal amount;

    @Column(name = "balance_before_transaction", nullable = false, precision = 18, scale = 2)
    @NotNull
    private BigDecimal balanceBeforeTransaction;

    @Column(name = "balance_after_transaction", nullable = false, precision = 18, scale = 2)
    @NotNull
    private BigDecimal balanceAfterTransaction;

    @Column(name = "is_fraudulent")
    private boolean isFraudulent; // to set after fraud checks (to update in the future)

    @Column(name = "created_at", nullable = false)
    @NotNull
    private Instant createdAt;

    @Column(name = "ip_address", nullable = false)
    @NotBlank
    private String ipAddress;

    public Transaction() {
    }

    public Transaction(TransactionType type, User user, BigDecimal amount, BigDecimal balanceBeforeTransaction) {
        if (balanceBeforeTransaction.subtract(amount).signum() == -1) {
            throw new InsufficientBalanceException("Account does not have a sufficient balance for this bill payment.");
        }
        this.type = type;
        this.user = user;
        this.amount = amount;
        this.createdAt = Instant.now();
        this.referenceNumber = Helper.generateAccountNumber(ConstantValues.TX_CODE, ConstantValues.BANK_CODE, ConstantValues.BRANCH_CODE, this.createdAt);
        // ipAddress
        // isFraudulent
        this.balanceBeforeTransaction = balanceBeforeTransaction;
        this.balanceAfterTransaction = balanceBeforeTransaction.subtract(this.amount);
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public Status getStatus() {
        return status;
    }

    // need to set after tx pushes through ?
    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceBeforeTransaction() {
        return balanceBeforeTransaction;
    }

    public void setBalanceBeforeTransaction(BigDecimal balanceBeforeTransaction) {
        this.balanceBeforeTransaction = balanceBeforeTransaction;
    }

    public BigDecimal getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(BigDecimal balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public boolean isFraudulent() {
        return isFraudulent;
    }

    public void setFraudulent(boolean fraudulent) {
        isFraudulent = fraudulent;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
