package com.pattisian.zetta.bank_backend.favorites.entity;

import com.pattisian.zetta.bank_backend.favorites.enums.FavoriteType;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
@Table(name = "favorites")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private FavoriteType type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Column(name = "biller_id")
    private Long billerId;

    @Column(name = "bank_id")
    private Long bankId;

    @Column(name = "account_name")
    @NotBlank
    private String accountName;

    @Column(name = "account_number")
    @NotBlank
    private String accountNumber;

    @Column(nullable = false)
    @NotBlank
    private String nickname;

    @Column(name = "created_at", nullable = false)
    @NotNull
    private Instant createdAt;

    public Favorite() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FavoriteType getType() {
        return type;
    }

    public void setType(FavoriteType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getBillerId() {
        return billerId;
    }

    public void setBillerId(Long billerId) {
        this.billerId = billerId;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public static class Builder {
            private FavoriteType type;
            private User user;
            private Long billerId;
            private Long bankId;
            private String accountName;
            private String accountNumber;
            private String nickname;
            private Instant createdAt;

            public Builder(
                    FavoriteType type,
                    User user,
                    String accountName,
                    String accountNumber,
                    String nickname
            ) {
                this.type = type;
                this.user = user;
                this.accountName = accountName;
                this.accountNumber = accountNumber;
                this.nickname = nickname;
                this.createdAt = Instant.now();
            }

            public Builder setBillerId(Long billerId) {
                this.billerId = billerId;
                return this;
            }

            public Builder setBankId(Long bankId) {
                this.bankId = bankId;
                return this;
            }

            public Favorite build() {
                return new Favorite(this);
            }
        }

        public static Builder builder(
                FavoriteType type,
                User user,
                String accountName,
                String accountNumber,
                String nickname
        ) {
            return new Builder(type, user, accountName, accountNumber, nickname);
        }

        private Favorite(Builder builder) {
            this.type = builder.type;
            this.user = builder.user;
            this.billerId = builder.billerId;
            this.bankId = builder.bankId;
            this.accountName = builder.accountName;
            this.accountNumber = builder.accountNumber;
            this.nickname = builder.nickname;
            this.createdAt = builder.createdAt;
        }


}
