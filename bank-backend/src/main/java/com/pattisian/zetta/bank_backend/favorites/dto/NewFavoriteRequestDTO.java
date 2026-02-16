package com.pattisian.zetta.bank_backend.favorites.dto;

import com.pattisian.zetta.bank_backend.favorites.enums.FavoriteType;

public class NewFavoriteRequestDTO {

    private FavoriteType type;
    private Long billerId;
    private Long bankId;
    private String accountName;
    private String accountNumber;
    private String nickname;

    public NewFavoriteRequestDTO(FavoriteType type, Long billerId, Long bankId, String accountName, String accountNumber, String nickname) {
        this.type = type;
        this.billerId = billerId;
        this.bankId = bankId;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.nickname = nickname;
    }

    public FavoriteType getType() {
        return type;
    }

    public void setType(FavoriteType type) {
        this.type = type;
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

}
