package com.pattisian.zetta.bank_backend.favorites.dto;

public class UpdateFavoriteRequestDTO {

    private String accountName;
    private String accountNumber;
    private String nickname;

    public UpdateFavoriteRequestDTO(String accountName, String accountNumber, String nickname) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.nickname = nickname;
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
