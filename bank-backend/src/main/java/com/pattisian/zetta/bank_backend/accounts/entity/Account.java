package com.pattisian.zetta.bank_backend.accounts.entity;

import com.pattisian.zetta.bank_backend.accounts.enums.AccountType;
import com.pattisian.zetta.bank_backend.accounts.enums.Status;
import com.pattisian.zetta.bank_backend.common.exception.AccountTypeException;
import com.pattisian.zetta.bank_backend.common.helpers.Helper;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

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

    @Column(name = "interest_rate_per_annum", nullable = false, precision = 5, scale = 4)
    @NotNull
    private BigDecimal interestRatePerAnnum;

    @Column(name = "closed_at")
    private Instant closedAt;

    @Transient
    public static final String BANK_CODE = "ZT";

    @Transient
    public static final String BRANCH_CODE = "001";

    @Transient
    private String accountTypeCode;

    public Account() {
    }

    public Account(AccountType accountType, BigDecimal availableBalance, User user) {

        BigDecimal interestRate;
        switch (accountType) {
            case SAVINGS:
                interestRate = AccountType.SAVINGS.getInterestRate();
                this.accountTypeCode = "SV";
                break;
            case CHECKING:
                interestRate = AccountType.CHECKING.getInterestRate();
                this.accountTypeCode = "CH";
                break;
            default:
                throw new AccountTypeException("Invalid account type");
        }

        this.accountType = accountType;
        this.availableBalance = availableBalance;
        this.user = user;
        this.status = Status.ACTIVE;
        this.interestRatePerAnnum = interestRate;
        this.openingDate = Instant.now();
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

    @PostPersist
    public void generateAccountNumber() {
        this.accountNumber = Helper.generateAccountNumber(this.id, this.accountTypeCode, BANK_CODE, BRANCH_CODE, this.openingDate);
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
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
                '}';
    }
}
