package com.pattisian.zetta.bank_backend.users.entity;

import com.pattisian.zetta.bank_backend.common.exception.AgeRequirementException;
import com.pattisian.zetta.bank_backend.users.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @Size(min = 2, max = 50, message = "Must be between 2 and 50 characters")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 2, max = 50, message = "Must be between 2 and 50 characters")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth; // 18 and above years old

    @Column(nullable = false, unique = true)
    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @Column(nullable = false, unique = true)
    @Size(min = 11, max = 12, message = "Must be between 11 and 12 digits")
    @NotBlank
    private String mobile;

    @Column(nullable = false, unique = true)
    @Size(min = 8, max = 15, message = "Must be between 8 and 15 characters")
    @NotBlank
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    @NotNull
    private Instant createdAt;

    private Instant updatedAt;

    @Transient
    public static final int LEGAL_AGE = 18;

    @Column(name = "session_active_now", nullable = false)
    @NotNull
    private boolean sessionActiveNow;

    @Column(name = "number_of_accounts_owned", nullable = false)
    @Min(0)
    private int numberOfAccountsOwned;

    public User() {

    }

    public User(String firstName, String middleName, String lastName, LocalDate dateOfBirth, String email, String mobile, String username, String password) {

        if (!isAdult(dateOfBirth)) {
            throw new AgeRequirementException(LEGAL_AGE);
        }

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.mobile = mobile;
        this.username = username;
        setPasswordHash(password);
        this.status = Status.ACTIVE;
        this.createdAt = Instant.now();
        this.sessionActiveNow = false;
        this.numberOfAccountsOwned = 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = password + "noEncoderYetSoToFollowPlease";
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isSessionActiveNow() {
        return sessionActiveNow;
    }

    public void setSessionActiveNow(boolean sessionActiveNow) {
        this.sessionActiveNow = sessionActiveNow;
    }

    public int getNumberOfAccountsOwned() {
        return numberOfAccountsOwned;
    }

    public void setNumberOfAccountsOwned(int numberOfAccountsOwned) {
        this.numberOfAccountsOwned = numberOfAccountsOwned;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", sessionActiveNow=" + sessionActiveNow +
                ", numberOfAccountsOwned=" + numberOfAccountsOwned +
                '}';
    }

    // Refactor to Helper
    public boolean isAdult(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        int difference = dateOfBirth.compareTo(today);

        // Refactor LEGAL_AGE to ConstantValues
        return Period.between(dateOfBirth, today).getYears() >= LEGAL_AGE;
    }
}
