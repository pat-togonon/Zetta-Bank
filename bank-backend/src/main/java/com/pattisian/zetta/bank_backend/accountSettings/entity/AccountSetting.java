package com.pattisian.zetta.bank_backend.accountSettings.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

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

    @Column(name = "daily_withdrawal_count_limit")
    private int dailyWithdrawalCountLimit;

    @Column(name = "daily_withdrawal_amount_limit", precision = 18, scale = 2)
    private BigDecimal dailyWithdrawalAmountLimit;

    @Column(name = "daily_bill_payment_count_limit")
    private int dailyBillPaymentCountLimit;

    @Column(name = "daily_bill_payment_amount_limit", precision = 18, scale = 2)
    private BigDecimal dailyBillPaymentAmountLimit;

    @Column(name = "daily_external_fund_transfer_count_limit")
    private int dailyExternalFundTransferCountLimit;

    @Column(name = "daily_external_fund_transfer_amount_limit", precision = 18, scale = 2)
    private BigDecimal dailyExternalFundTransferAmountLimit;

    @Column(name = "updated_at", nullable = false)
    @NotNull
    private Instant updatedAt;

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
        this.dailyWithdrawalCountLimit = dailyWithdrawalCountLimit;
    }

    public BigDecimal getDailyWithdrawalAmountLimit() {
        return dailyWithdrawalAmountLimit;
    }

    public void setDailyWithdrawalAmountLimit(BigDecimal dailyWithdrawalAmountLimit) {
        this.dailyWithdrawalAmountLimit = dailyWithdrawalAmountLimit;
    }

    public int getDailyBillPaymentCountLimit() {
        return dailyBillPaymentCountLimit;
    }

    public void setDailyBillPaymentCountLimit(int dailyBillPaymentCountLimit) {
        this.dailyBillPaymentCountLimit = dailyBillPaymentCountLimit;
    }

    public BigDecimal getDailyBillPaymentAmountLimit() {
        return dailyBillPaymentAmountLimit;
    }

    public void setDailyBillPaymentAmountLimit(BigDecimal dailyBillPaymentAmountLimit) {
        this.dailyBillPaymentAmountLimit = dailyBillPaymentAmountLimit;
    }

    public int getDailyExternalFundTransferCountLimit() {
        return dailyExternalFundTransferCountLimit;
    }

    public void setDailyExternalFundTransferCountLimit(int dailyExternalFundTransferCountLimit) {
        this.dailyExternalFundTransferCountLimit = dailyExternalFundTransferCountLimit;
    }

    public BigDecimal getDailyExternalFundTransferAmountLimit() {
        return dailyExternalFundTransferAmountLimit;
    }

    public void setDailyExternalFundTransferAmountLimit(BigDecimal dailyExternalFundTransferAmountLimit) {
        this.dailyExternalFundTransferAmountLimit = dailyExternalFundTransferAmountLimit;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static class Builder {
            private Account account;
            private int dailyWithdrawalCountLimit;
            private BigDecimal dailyWithdrawalAmountLimit;
            private int dailyBillPaymentCountLimit;
            private BigDecimal dailyBillPaymentAmountLimit;
            private int dailyExternalFundTransferCountLimit;
            private BigDecimal dailyExternalFundTransferAmountLimit;
            private Instant updatedAt;

            public Builder(
                    Account account
            ) {
                this.account = account;
                this.updatedAt = Instant.now();
            }

            public Builder setDailyWithdrawalCountLimit(int dailyWithdrawalCountLimit) {
                this.dailyWithdrawalCountLimit = dailyWithdrawalCountLimit;
                return this;
            }

            public Builder setDailyWithdrawalAmountLimit(BigDecimal dailyWithdrawalAmountLimit) {
                this.dailyWithdrawalAmountLimit = dailyWithdrawalAmountLimit;
                return this;
            }

            public Builder setDailyBillPaymentCountLimit(int dailyBillPaymentCountLimit) {
                this.dailyBillPaymentCountLimit = dailyBillPaymentCountLimit;
                return this;
            }

            public Builder setDailyBillPaymentAmountLimit (BigDecimal dailyBillPaymentAmountLimit) {
                this.dailyBillPaymentAmountLimit = dailyBillPaymentAmountLimit;
                return this;
            }

            public Builder setDailyExternalFundTransferCountLimit(int dailyExternalFundTransferCountLimit) {
                this.dailyExternalFundTransferCountLimit = dailyExternalFundTransferCountLimit;
                return this;
            }

            public Builder setDailyExternalFundTransferAmountLimit(BigDecimal dailyExternalFundTransferAmountLimit) {
                this.dailyExternalFundTransferAmountLimit = dailyExternalFundTransferAmountLimit;
                return this;
            }

            public AccountSetting build() {
                return new AccountSetting(this);
            }
        }

        public static Builder build(Account account) {
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
                this.updatedAt = builder.updatedAt;
        }


}
