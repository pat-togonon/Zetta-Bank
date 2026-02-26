package com.pattisian.zetta.bank_backend.transactions.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class InternalFundTransfer extends Transaction {

    @Column(name = "source_account_id", nullable = false)
    @NotNull
    private Long sourceAccountId;

    @Column(name = "internal_destination_account_id", nullable = false)
    @NotNull
    private Long internalDestinationAccountId;

    public InternalFundTransfer() {
        super();
    }

    public InternalFundTransfer(Long sourceAccountId, Long internalDestinationAccountId) {
        this.sourceAccountId = sourceAccountId;
        this.internalDestinationAccountId = internalDestinationAccountId;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public Long getInternalDestinationAccount() {
        return internalDestinationAccountId;
    }

    public void setInternalDestinationAccount(Long internalDestinationAccountId) {
        this.internalDestinationAccountId = this.internalDestinationAccountId;
    }
}
