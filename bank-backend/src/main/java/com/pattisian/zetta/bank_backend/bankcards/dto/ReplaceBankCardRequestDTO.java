package com.pattisian.zetta.bank_backend.bankcards.dto;

public class ReplaceBankCardRequestDTO {

    private Long primaryAccountId;

    public ReplaceBankCardRequestDTO(Long primaryAccountId) {
        this.primaryAccountId = primaryAccountId;
    }

    public Long getPrimaryAccountId() {
        return primaryAccountId;
    }

    public void setPrimaryAccountId(Long primaryAccountId) {
        this.primaryAccountId = primaryAccountId;
    }
}
