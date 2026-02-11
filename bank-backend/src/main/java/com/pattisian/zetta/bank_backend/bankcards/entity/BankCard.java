package com.pattisian.zetta.bank_backend.bankcards.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.bankcards.enums.Status;
import com.pattisian.zetta.bank_backend.common.helpers.Helper;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Entity
@Table(name = "bank_cards")
public class BankCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "primary_account_id", nullable = false)
    @NotNull
    private Account primaryAccount;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private User user;

    @Column(name = "card_number_hash") //  nullable = false
    // @NotBlank
    private String cardNumberHash;

    @Column(name = "cvv_hash") //  nullable = false
    // @NotBlank
    private String cvvHash;

    @Column(name = "card_number_last_four_digits")
    private String cardNumberLastFourDigits;

    @Column(name = "issue_date", nullable = false)
    @NotNull
    private Instant issueDate;

    @Column(name = "expiration_date", nullable = false)
    @NotNull
    private Instant expirationDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    @Transient
    private static final String BANK_BIN = "121994";

    @Transient
    private String bankCardNumber;

    @Column(name = "last_updated_at")
    private Instant lastUpdatedAt;

    public BankCard() {
    }

    public BankCard(Account primaryAccount, User user) {
        this.primaryAccount = primaryAccount;
        this.user = user;
        this.status = Status.ACTIVE;
        this.issueDate = Instant.now();
        this.expirationDate = this.issueDate
                .atZone(ZoneId.of("Asia/Manila"))
                .plusYears(3)
                .toInstant();
        this.bankCardNumber = this.generateCardNumber();
        this.cardNumberHash = this.generateCardNumberHash();
        this.setCardNumberLastFourDigits();
        this.cvvHash = this.generateCvvHash();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getPrimaryAccount() {
        return primaryAccount;
    }

    public void setPrimaryAccount(Account primaryAccount) {
        this.primaryAccount = primaryAccount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCardNumberHash() {
        return cardNumberHash;
    }

    public String generateCardNumber() {
        String sequence = Helper.generateNumberSequence();
        String payloadDigits = BANK_BIN + sequence;
        int checkDigit = Helper.calculateCheckDigit(payloadDigits);
        return payloadDigits + checkDigit;
    }

    public String getCvvHash() {
        return cvvHash;
    }

    public String generateCvv() {
        return Helper.generateCvv();
    }

    public String generateCvvHash() {
        return this.generateCvv() + "waitLangForEncoder";
    }

    public Instant getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Instant issueDate) {
        this.issueDate = issueDate;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String generateCardNumberHash() {
       //password encoder here - to follow. Need to set up Security first. Will work on my entities first.
        return this.bankCardNumber + "WillHashSOOn";
    }

    public String getCardNumberLastFourDigits() {
        return cardNumberLastFourDigits;
    }

    public void setCardNumberLastFourDigits() {
        int n = this.bankCardNumber.length();
        this.cardNumberLastFourDigits = this.bankCardNumber.substring(n - 4);
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public Instant getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Instant lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    @PreUpdate
    public void preUpdateLastUpdatedAt() {
        this.lastUpdatedAt = Instant.now();
    }


    @Override
    public String toString() {
        return "BankCard{" +
                "id=" + id +
                ", primaryAccount=" + primaryAccount +
                ", user=" + user +
                ", cardNumberLastFourDigits='" + cardNumberLastFourDigits + '\'' +
                ", issueDate=" + issueDate +
                ", expirationDate=" + expirationDate +
                ", status=" + status +
                ", lastUpdatedAt=" + lastUpdatedAt +
                '}';
    }
}
