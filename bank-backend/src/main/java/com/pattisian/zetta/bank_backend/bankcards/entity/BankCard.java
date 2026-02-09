package com.pattisian.zetta.bank_backend.bankcards.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.bankcards.enums.Status;
import com.pattisian.zetta.bank_backend.common.helpers.Helper;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.ZoneId;

@Entity
@Table(name = "bank_cards")
public class BankCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "primary_account_id", nullable = false, unique = true)
    @NotNull
    private Account primaryAccount;

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

    public BankCard() {
    }

    public BankCard(Account primaryAccount) {
        this.primaryAccount = primaryAccount;
        this.status = Status.ACTIVE;
        this.issueDate = Instant.now();
        this.expirationDate = this.issueDate
                .atZone(ZoneId.of("Asia/Manila"))
                .plusYears(3)
                .toInstant();
        this.bankCardNumber = this.generateCardNumber();
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

    public void setCardNumberHash() {
       //password encoder here - to follow. Need to set up Security first. Will work on my entities first.
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


    @Override
    public String toString() {
        return "BankCard{" +
                "id=" + id +
                ", primaryAccount=" + primaryAccount +
                ", cardNumberLastFourDigits='" + cardNumberLastFourDigits + '\'' +
                ", issueDate=" + issueDate +
                ", expirationDate=" + expirationDate +
                ", status=" + status +
                '}';
    }
}
