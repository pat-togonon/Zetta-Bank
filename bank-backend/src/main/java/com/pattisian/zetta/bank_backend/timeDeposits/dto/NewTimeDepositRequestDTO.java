package com.pattisian.zetta.bank_backend.timeDeposits.dto;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.timeDeposits.enums.Term;

import java.math.BigDecimal;

public class NewTimeDepositRequestDTO {

    private Term termMonths;
    private Long sourceAccountId;
    private Long payoutAccountId;
    private boolean isAutoRenew;
    private BigDecimal principal;

    public NewTimeDepositRequestDTO(Term termMonths, Long sourceAccountId, Long payoutAccountId, boolean isAutoRenew, BigDecimal principal) {
        this.termMonths = termMonths;
        this.sourceAccountId = sourceAccountId;
        this.payoutAccountId = payoutAccountId;
        this.isAutoRenew = isAutoRenew;
        this.principal = principal;
    }

    public Term getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(Term termMonths) {
        this.termMonths = termMonths;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public Long getPayoutAccountId() {
        return payoutAccountId;
    }

    public void setPayoutAccountId(Long payoutAccountId) {
        this.payoutAccountId = payoutAccountId;
    }

    public boolean isAutoRenew() {
        return isAutoRenew;
    }

    public void setAutoRenew(boolean autoRenew) {
        isAutoRenew = autoRenew;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }
}
