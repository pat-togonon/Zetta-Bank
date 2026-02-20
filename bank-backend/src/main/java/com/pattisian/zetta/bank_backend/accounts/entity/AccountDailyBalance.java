package com.pattisian.zetta.bank_backend.accounts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "account_daily_balance")
public class AccountDailyBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "closing_balance", nullable = false, precision = 18, scale = 2)
    @NotNull
    private BigDecimal closingBalance;

    @NotNull
    @Column(nullable = false)
    private Instant date;

    public AccountDailyBalance(Long id, Account account, BigDecimal closingBalance) {
        this.id = id;
        this.account = account;
        this.closingBalance = closingBalance;
        this.date = Instant.now();
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

    public BigDecimal getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(BigDecimal closingBalance) {
        this.closingBalance = closingBalance;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
}
