package com.pattisian.zetta.bank_backend.transactions.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.common.helpers.Helper;
import com.pattisian.zetta.bank_backend.timeDeposits.entity.TimeDeposit;
import com.pattisian.zetta.bank_backend.transactions.enums.Status;
import com.pattisian.zetta.bank_backend.transactions.enums.TransactionType;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

// For all status, might need better checks. If Id is generated, PostConstruct Status e.g

// Also check out @EntityListeners(AuditingEntityListener.class)

// For constants - can create a class to store all that

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "reference_number", unique = true)
    private String referenceNumber; // to derive from id Pat

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    // for internal transactions - account to account
    @ManyToOne
    @JoinColumn(name = "internal_destination_account_id")
    private Account internalFundTransferDestinationAccount;

    //for external bank account transactions

    @Column(name = "external_destination_bank_id")
    private Long externalDestinationBankId;

    @Column(name = "external_destination_account_number")
    private String externalDestinationAccountNumber;

    @Column(name = "external_destination_account_name")
    private String externalDestinationAccountName;

    // for Bill Payment
    @Column(name = "biller_id")
    private Long billerId;

    // for Time Depo
    @ManyToOne
    @JoinColumn(name = "related_time_deposit_id")
    private TimeDeposit relatedTimeDeposit;

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

    @Transient
    private static final String TX_CODE = "TX";

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    @PostPersist
    public void generateReferenceNumber() {
        this.referenceNumber = Helper.generateAccountNumber(this.id, TX_CODE, Account.BANK_CODE, Account.BRANCH_CODE, createdAt);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getInternalFundTransferDestinationAccount() {
        return internalFundTransferDestinationAccount;
    }

    public void setInternalFundTransferDestinationAccount(Account internalFundTransferDestinationAccount) {
        this.internalFundTransferDestinationAccount = internalFundTransferDestinationAccount;
    }

    public Long getExternalDestinationBankId() {
        return externalDestinationBankId;
    }

    public void setExternalDestinationBankId(Long externalDestinationBankId) {
        this.externalDestinationBankId = externalDestinationBankId;
    }

    public String getExternalDestinationAccountNumber() {
        return externalDestinationAccountNumber;
    }

    public void setExternalDestinationAccountNumber(String externalDestinationAccountNumber) {
        this.externalDestinationAccountNumber = externalDestinationAccountNumber;
    }

    public String getExternalDestinationAccountName() {
        return externalDestinationAccountName;
    }

    public void setExternalDestinationAccountName(String externalDestinationAccountName) {
        this.externalDestinationAccountName = externalDestinationAccountName;
    }

    public Long getBillerId() {
        return billerId;
    }

    public void setBillerId(Long billerId) {
        this.billerId = billerId;
    }

    public TimeDeposit getRelatedTimeDeposit() {
        return relatedTimeDeposit;
    }

    public void setRelatedTimeDeposit(TimeDeposit relatedTimeDeposit) {
        this.relatedTimeDeposit = relatedTimeDeposit;
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

    public static class Builder {

        private TransactionType type;

        private Status status;

        private User user;

        private Account sourceAccount;

        private Account internalFundTransferDestinationAccount;

        private Long externalDestinationBankId;

        private String externalDestinationAccountNumber;

        private String externalDestinationAccountName;

        private Long billerId;

        private TimeDeposit relatedTimeDeposit;

        private BigDecimal amount;

        private BigDecimal balanceBeforeTransaction;

        private BigDecimal balanceAfterTransaction;

        private boolean isFraudulent;

        private Instant createdAt;

        public Builder(
                TransactionType type,
                User user,
                BigDecimal amount,
                Account sourceAccount
        ) {
            this.type = type;
            this.user = user;
            this.sourceAccount = sourceAccount;
            this.amount = amount;
            this.createdAt = Instant.now();
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder internalFundTransferDestinationAccount(Account internalFundTransferDestinationAccount) {
            this.internalFundTransferDestinationAccount = internalFundTransferDestinationAccount;
            return this;
        }

        public Builder externalDestinationBankId(Long externalDestinationBankId) {
            this.externalDestinationBankId = externalDestinationBankId;
            return this;
        }

        public Builder externalDestinationAccountNumber(String externalDestinationAccountNumber) {
            this.externalDestinationAccountNumber = externalDestinationAccountNumber;
            return this;
        }

        public Builder externalDestinationAccountName(String externalDestinationAccountName) {
            this.externalDestinationAccountName = externalDestinationAccountName;
            return this;
        }

        public Builder billerId(Long billerId) {
            this.billerId = billerId;
            return this;
        }

        public Builder relatedTimeDeposit(TimeDeposit relatedTimeDeposit) {
            this.relatedTimeDeposit = relatedTimeDeposit;
            return this;
        }

        public Builder balanceBeforeTransaction(BigDecimal balanceBeforeTransaction) {
            this.balanceBeforeTransaction = balanceBeforeTransaction;
            return this;
        }

        public Builder balanceAfterTransaction(BigDecimal balanceAfterTransaction) {
            this.balanceAfterTransaction = balanceAfterTransaction;
            return this;
        }

        public Builder isFraudulent(boolean isFraudulent) {
            this.isFraudulent = isFraudulent;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }

    public static Builder builder(
            TransactionType type,
            User user,
            BigDecimal amount,
            Account sourceAccount
    ) {
        return new Builder(type, user, amount, sourceAccount);
    }

    private Transaction(Builder builder) {
        this.type = builder.type;
        this.status = builder.status;
        this.user = builder.user;
        this.sourceAccount = builder.sourceAccount;
        this.internalFundTransferDestinationAccount = builder.internalFundTransferDestinationAccount;
        this.externalDestinationBankId = builder.externalDestinationBankId;
        this.externalDestinationAccountNumber = builder.externalDestinationAccountNumber;
        this.externalDestinationAccountName = builder.externalDestinationAccountName;
        this.billerId = builder.billerId;
        this.relatedTimeDeposit = builder.relatedTimeDeposit;
        this.amount = builder.amount;
        this.balanceBeforeTransaction = builder.balanceBeforeTransaction;
        this.balanceAfterTransaction = builder.balanceAfterTransaction;
        this.isFraudulent = builder.isFraudulent;
        this.createdAt = builder.createdAt;
    }

}
