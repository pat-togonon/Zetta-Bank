package com.pattisian.zetta.bank_backend.bankcards.dto;

import com.pattisian.zetta.bank_backend.bankcards.enums.Status;

public class UpdateBankCardRequestDTO {

    private Long primaryAccountId;
    private Status status;

    public UpdateBankCardRequestDTO(Long primaryAccountId, Status status) {
        this.primaryAccountId = primaryAccountId;
        this.status = status;
    }

    public Long getPrimaryAccountId() {
        return primaryAccountId;
    }

    public void setPrimaryAccount(Long primaryAccount) {
        this.primaryAccountId = primaryAccountId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UpdateBankCardRequestDTO{" +
                "primaryAccountId=" + primaryAccountId +
                ", status=" + status +
                '}';
    }
}
