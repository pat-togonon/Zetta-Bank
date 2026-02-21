package com.pattisian.zetta.bank_backend.accounts.entity;

import com.pattisian.zetta.bank_backend.accounts.enums.AccountType;
import com.pattisian.zetta.bank_backend.accounts.enums.Status;
import com.pattisian.zetta.bank_backend.common.enums.Currency;
import com.pattisian.zetta.bank_backend.common.exception.InsufficientBalanceException;
import com.pattisian.zetta.bank_backend.common.exception.NegativeAmountException;
import com.pattisian.zetta.bank_backend.common.helpers.Helper;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.YearMonth;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_type", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "account_number", unique = true) // derived Pat
    private String accountNumber;

    @Column(name = "available_balance", nullable = false, precision = 18, scale = 2)
    @NotNull
    private BigDecimal availableBalance;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "opening_date", nullable = false)
    @NotNull
    private Instant openingDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private Status status;

    @Column(name = "interest_rate_per_annum", nullable = false, precision = 7, scale = 4)
    @NotNull
    private BigDecimal interestRatePerAnnum;

    @Column(name = "closed_at")
    private Instant closedAt;

    @Transient
    public static final String BANK_CODE = "ZT";

    @Transient
    public static final String BRANCH_CODE = "001";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private Currency currency;

    @Version
    private Long version;

    @Transient
    private String accountTypeCode;

    @Column(name = "last_interest_credited_month")
    private YearMonth lastInterestCreditedMonth;

    public Account() {
    }

    public Account(AccountType accountType, BigDecimal availableBalance, User user, Currency currency) {
        this.accountType = accountType;
        this.accountTypeCode = accountType.getAccountTypeCode();
        this.availableBalance = availableBalance;
        this.user = user;
        this.status = Status.ACTIVE;
        this.interestRatePerAnnum = accountType.getInterestRate();
        this.openingDate = Instant.now();
        this.accountNumber = this.generateAccountNumber();
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String generateAccountNumber() {
        return Helper.generateAccountNumber(this.accountTypeCode, BANK_CODE, BRANCH_CODE, this.openingDate);
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void deductAmountFromBalance(BigDecimal amount) {
        if (amount.signum() == -1) {
            throw new NegativeAmountException("Invalid amount.");
        }

        if (this.status != Status.ACTIVE) {
            throw new IllegalStateException("Cannot deduct money from a non-active account.");
        }
        if (this.availableBalance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for this transaction.");
        }
        this.availableBalance = this.availableBalance.subtract(amount);
    }

    public void addAmountToBalance(BigDecimal amount) {
        if (amount.signum() == -1) {
            throw new NegativeAmountException("Invalid amount.");
        }

        this.availableBalance = this.availableBalance.add(amount);
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Instant openingDate) {
        this.openingDate = openingDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getInterestRatePerAnnum() {
        return interestRatePerAnnum;
    }

    public void setInterestRatePerAnnum(BigDecimal interestRatePerAnnum) {
        this.interestRatePerAnnum = interestRatePerAnnum;
    }

    public Instant getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Instant closedAt) {
        this.closedAt = closedAt;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public Long getVersion() {
        return version;
    }

    public YearMonth getLastInterestCreditedMonth() {
        return lastInterestCreditedMonth;
    }

    public void setLastInterestCreditedMonth(YearMonth lastInterestCreditedMonth) {
        this.lastInterestCreditedMonth = lastInterestCreditedMonth;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountType=" + accountType +
                ", accountNumber='" + accountNumber + '\'' +
                ", availableBalance=" + availableBalance +
                ", user=" + user +
                ", openingDate=" + openingDate +
                ", status=" + status +
                ", interestRatePerAnnum=" + interestRatePerAnnum +
                ", closedAt=" + closedAt +
                ", currency=" + currency +
                ", accountTypeCode='" + accountTypeCode + '\'' +
                '}';
    }
}
