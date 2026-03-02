package com.pattisian.zetta.bank_backend.transactions.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.common.ConstantValues;
import com.pattisian.zetta.bank_backend.transactions.enums.TransactionType;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class InterestCredit extends Transaction {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payout_account_id", nullable = false)
    @NotNull
    private Account payoutAccount;

    @Column(name = "interest_credit_origin", nullable = false)
    @NotBlank
    private final String interestCreditOrigin = ConstantValues.INTEREST_CREDIT_ORIGIN;

    public InterestCredit() {

    }
    // TransactionType type, User user, BigDecimal amount, BigDecimal balanceBeforeTransaction
    public InterestCredit(User user, BigDecimal amount, Account payoutAccount, BigDecimal balanceAfterTransaction) {
        super(TransactionType.INTEREST_CREDIT, user, amount, payoutAccount.getAvailableBalance(), balanceAfterTransaction);
        this.payoutAccount = payoutAccount;
    }
}
