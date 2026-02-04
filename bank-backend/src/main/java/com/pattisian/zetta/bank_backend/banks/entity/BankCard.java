package com.pattisian.zetta.bank_backend.banks.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.banks.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
@Table(name = "bank_cards")
public class BankCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "primary_account_id", nullable = false, unique = true)
    @NotNull
    private Account primaryAccount;

    @Column(name = "card_number", unique = true)
    private String cardNumber;

    @Column(name = "issue_date", nullable = false)
    @NotNull
    private Instant issueDate;

    @Column(name = "expiration_date")
    private Instant expirationDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    public BankCard() {
    }

    public BankCard(Account primaryAccount) {
        this.primaryAccount = primaryAccount;
        this.status = Status.ACTIVE;
        this.issueDate = Instant.now();
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

    public String getCardNumber() {
        return cardNumber;
    }

    // to work on - Luhn's algo
    @PostPersist
    public void generateCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    @Override
    public String toString() {
        return "BankCard{" +
                "id=" + id +
                ", primaryAccount=" + primaryAccount +
                ", cardNumber='" + cardNumber + '\'' +
                ", issueDate=" + issueDate +
                ", expirationDate=" + expirationDate +
                ", status=" + status +
                '}';
    }
}
