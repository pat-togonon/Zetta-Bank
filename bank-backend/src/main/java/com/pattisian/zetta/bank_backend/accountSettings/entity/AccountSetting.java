package com.pattisian.zetta.bank_backend.accountSettings.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.common.ConstantValues;
import com.pattisian.zetta.bank_backend.common.exception.InvalidCountLimit;
import com.pattisian.zetta.bank_backend.common.exception.NegativeAmountException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "account_settings")
public class AccountSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    @NotNull
    private Account account;

    @Column(name = "daily_withdrawal_count_limit", nullable = false)
    @Min(0)
    private int dailyWithdrawalCountLimit;

    @Column(name = "daily_withdrawal_amount_limit", precision = 18, scale = 2, nullable = false)
    @NotNull
    @Min(0)
    private BigDecimal dailyWithdrawalAmountLimit;

    @Min(0)
    @Column(name = "daily_bill_payment_count_limit", nullable = false)
    private int dailyBillPaymentCountLimit;

    @Column(name = "daily_bill_payment_amount_limit", precision = 18, scale = 2, nullable = false)
    @Min(0)
    @NotNull
    private BigDecimal dailyBillPaymentAmountLimit;

    @Column(name = "daily_external_fund_transfer_count_limit", nullable = false)
    @Min(0)
    private int dailyExternalFundTransferCountLimit;

    @Column(name = "daily_external_fund_transfer_amount_limit", precision = 18, scale = 2, nullable = false)
    @Min(0)
    @NotNull
    private BigDecimal dailyExternalFundTransferAmountLimit;

    @Column(name = "updated_at", nullable = false)
    @NotNull
    private Instant updatedAt;

    // Usage counts

    @Column(name = "daily_withdrawal_count_used", nullable = false)
    @Min(0)
    private int dailyWithdrawalCountUsed = 0;

    @Column(name = "daily_withdrawal_amount_used", precision = 18, scale = 2, nullable = false)
    @NotNull
    @Min(0)
    private BigDecimal dailyWithdrawalAmountUsed = BigDecimal.ZERO;

    @Column(name = "daily_bill_payment_count_used", nullable = false)
    @Min(0)
    private int dailyBillPaymentCountUsed = 0;

    @Column(name = "daily_bill_payment_amount_used", precision = 18, scale = 2, nullable = false)
    @NotNull
    @Min(0)
    private BigDecimal dailyBillPaymentAmountUsed = BigDecimal.ZERO;

    @Column(name = "daily_external_fund_transfer_count_used", nullable = false)
    @Min(0)
    private int dailyExternalFundTransferCountUsed = 0;

    @Column(name = "daily_external_fund_transfer_amount_used", precision = 18, scale = 2, nullable = false)
    @NotNull
    @Min(0)
    private BigDecimal dailyExternalFundTransferAmountUsed = BigDecimal.ZERO;

    @Column(name = "last_reset_date")
    private LocalDate lastResetDate;


    public AccountSetting() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getDailyWithdrawalCountLimit() {
        return dailyWithdrawalCountLimit;
    }

    public void setDailyWithdrawalCountLimit(int dailyWithdrawalCountLimit) {
        if (dailyWithdrawalCountLimit < 0) {
            throw new InvalidCountLimit("Count limits cannot be negative.");
        }
        this.dailyWithdrawalCountLimit = dailyWithdrawalCountLimit;
    }

    public BigDecimal getDailyWithdrawalAmountLimit() {
        return dailyWithdrawalAmountLimit;
    }

    public void setDailyWithdrawalAmountLimit(BigDecimal dailyWithdrawalAmountLimit) {
        if (dailyWithdrawalAmountLimit.signum() == -1) {
            throw new NegativeAmountException("Amount limits cannot be negative.");
        }
        this.dailyWithdrawalAmountLimit = dailyWithdrawalAmountLimit;
    }

    public int getDailyBillPaymentCountLimit() {
        return dailyBillPaymentCountLimit;
    }

    public void setDailyBillPaymentCountLimit(int dailyBillPaymentCountLimit) {
        if (dailyBillPaymentCountLimit < 0) {
            throw new InvalidCountLimit("Count limits cannot be negative.");
        }
        this.dailyBillPaymentCountLimit = dailyBillPaymentCountLimit;
    }

    public BigDecimal getDailyBillPaymentAmountLimit() {
        return dailyBillPaymentAmountLimit;
    }

    public void setDailyBillPaymentAmountLimit(BigDecimal dailyBillPaymentAmountLimit) {
        if (dailyBillPaymentAmountLimit.signum() == -1) {
            throw new NegativeAmountException("Amount limits cannot be negative.");
        }
        this.dailyBillPaymentAmountLimit = dailyBillPaymentAmountLimit;
    }

    public int getDailyExternalFundTransferCountLimit() {
        return dailyExternalFundTransferCountLimit;
    }

    public void setDailyExternalFundTransferCountLimit(int dailyExternalFundTransferCountLimit) {
        if (dailyExternalFundTransferCountLimit < 0) {
            throw new InvalidCountLimit("Count limits cannot be negative.");
        }
        this.dailyExternalFundTransferCountLimit = dailyExternalFundTransferCountLimit;
    }

    public BigDecimal getDailyExternalFundTransferAmountLimit() {
        return dailyExternalFundTransferAmountLimit;
    }

    public void setDailyExternalFundTransferAmountLimit(BigDecimal dailyExternalFundTransferAmountLimit) {
        if (dailyExternalFundTransferAmountLimit.signum() == -1) {
            throw new NegativeAmountException("Amount limits cannot be negative..");
        }
        this.dailyExternalFundTransferAmountLimit = dailyExternalFundTransferAmountLimit;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getDailyWithdrawalCountUsed() {
        return dailyWithdrawalCountUsed;
    }

    public void addCountToDailyWithdrawalCountUsed() {
        this.dailyWithdrawalCountUsed++;
    }

    public BigDecimal getDailyWithdrawalAmountUsed() {
        return dailyWithdrawalAmountUsed;
    }

    public void addAmountToDailyWithdrawalAmountUsed(BigDecimal amountUsed) {
        if (amountUsed.signum() == -1) {
            throw new NegativeAmountException("Amount limits cannot be negative.");
        }
        this.dailyWithdrawalAmountUsed = this.dailyWithdrawalAmountUsed.add(amountUsed);
    }

    public int getDailyBillPaymentCountUsed() {
        return dailyBillPaymentCountUsed;
    }

    public void addCountToDailyBillPaymentCountUsed() {
        this.dailyBillPaymentCountUsed++;
    }

    public BigDecimal getDailyBillPaymentAmountUsed() {
        return dailyBillPaymentAmountUsed;
    }

    public void addAmountToDailyBillPaymentAmountUsed(BigDecimal amountUsed) {
        if (amountUsed.signum() == -1) {
            throw new NegativeAmountException("Amount used cannot be negative..");
        }
        this.dailyBillPaymentAmountUsed = this.dailyBillPaymentAmountUsed.add(amountUsed);
    }

    public int getDailyExternalFundTransferCountUsed() {
        return dailyExternalFundTransferCountUsed;
    }

    public void addCountToDailyExternalFundTransferCountUsed() {
        this.dailyExternalFundTransferCountUsed++;
    }

    public BigDecimal getDailyExternalFundTransferAmountUsed() {
        return dailyExternalFundTransferAmountUsed;
    }

    public void addAmountToDailyExternalFundTransferAmountUsed(BigDecimal amountUsed) {
        if (amountUsed.signum() == -1) {
            throw new NegativeAmountException("Amount used cannot be negative.");
        }
        this.dailyExternalFundTransferAmountUsed = this.dailyExternalFundTransferAmountUsed.add(amountUsed);
    }

    public LocalDate getLastResetDate() {
        return lastResetDate;
    }

    public void setLastResetDate(LocalDate lastResetDate) {
        this.lastResetDate = lastResetDate;
    }

    @PrePersist
    public void prePersistDates() {
        this.updatedAt = Instant.now();
        this.lastResetDate = LocalDate.now(ConstantValues.BANK_ZONE);
    }

    @PreUpdate
    public void preUpdateDate() {
        this.updatedAt = Instant.now();
    }

    public void resetDailyUsage() {
        LocalDate today = LocalDate.now(ConstantValues.BANK_ZONE);
        if (!today.equals(this.lastResetDate)) {
            this.dailyWithdrawalCountUsed = 0;
            this.dailyBillPaymentAmountUsed = BigDecimal.ZERO;
            this.dailyBillPaymentCountUsed = 0;
            this.dailyBillPaymentAmountUsed = BigDecimal.ZERO;
            this.dailyExternalFundTransferCountUsed = 0;
            this.dailyExternalFundTransferAmountUsed = BigDecimal.ZERO;
            this.lastResetDate = today;
        }
    }


    public static class Builder {
            private Account account;
            private int dailyWithdrawalCountLimit = ConstantValues.DAILY_WITHDRAWAL_COUNT_LIMIT;
            private BigDecimal dailyWithdrawalAmountLimit = ConstantValues.DAILY_WITHDRAWAL_AMOUNT_LIMIT;
            private int dailyBillPaymentCountLimit = ConstantValues.DAILY_BILL_PAYMENT_COUNT_LIMIT;
            private BigDecimal dailyBillPaymentAmountLimit = ConstantValues.DAILY_BILL_PAYMENT_AMOUNT_LIMIT;
            private int dailyExternalFundTransferCountLimit = ConstantValues.DAILY_EXTERNAL_FUND_TRANSFER_COUNT_LIMIT;
            private BigDecimal dailyExternalFundTransferAmountLimit = ConstantValues.DAILY_EXTERNAL_FUND_TRANSFER_AMOUNT_LIMIT;

            public Builder(Account account) {
                this.account = account;
            }

            public AccountSetting build() {
                return new AccountSetting(this);
            }
        }

        public static Builder builder(Account account) {
            return new Builder(account);
        }

        private AccountSetting(Builder builder) {
                this.account = builder.account;
                this.dailyWithdrawalCountLimit = builder.dailyWithdrawalCountLimit;
                this.dailyWithdrawalAmountLimit = builder.dailyWithdrawalAmountLimit;
                this.dailyBillPaymentCountLimit = builder.dailyBillPaymentCountLimit;
                this.dailyBillPaymentAmountLimit = builder.dailyBillPaymentAmountLimit;
                this.dailyExternalFundTransferCountLimit = builder.dailyExternalFundTransferCountLimit;
                this.dailyExternalFundTransferAmountLimit = builder.dailyExternalFundTransferAmountLimit;
        }

    @Override
    public String toString() {
        return "AccountSetting{" +
                "id=" + id +
                ", account=" + account +
                ", dailyWithdrawalCountLimit=" + dailyWithdrawalCountLimit +
                ", dailyWithdrawalAmountLimit=" + dailyWithdrawalAmountLimit +
                ", dailyBillPaymentCountLimit=" + dailyBillPaymentCountLimit +
                ", dailyBillPaymentAmountLimit=" + dailyBillPaymentAmountLimit +
                ", dailyExternalFundTransferCountLimit=" + dailyExternalFundTransferCountLimit +
                ", dailyExternalFundTransferAmountLimit=" + dailyExternalFundTransferAmountLimit +
                ", updatedAt=" + updatedAt +
                ", dailyWithdrawalCountUsed=" + dailyWithdrawalCountUsed +
                ", dailyWithdrawalAmountUsed=" + dailyWithdrawalAmountUsed +
                ", dailyBillPaymentCountUsed=" + dailyBillPaymentCountUsed +
                ", dailyBillPaymentAmountUsed=" + dailyBillPaymentAmountUsed +
                ", dailyExternalFundTransferCountUsed=" + dailyExternalFundTransferCountUsed +
                ", dailyExternalFundTransferAmountUsed=" + dailyExternalFundTransferAmountUsed +
                ", lastResetDate=" + lastResetDate +
                '}';
    }
}
