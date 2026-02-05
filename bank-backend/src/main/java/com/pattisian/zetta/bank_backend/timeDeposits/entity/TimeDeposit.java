package com.pattisian.zetta.bank_backend.timeDeposits.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.common.exception.TermDurationException;
import com.pattisian.zetta.bank_backend.common.helpers.Helper;
import com.pattisian.zetta.bank_backend.timeDeposits.enums.Status;
import com.pattisian.zetta.bank_backend.timeDeposits.enums.Term;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;

@Entity
@Table(name = "time_deposits")
public class TimeDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference_number", unique = true, nullable = false)
    @NotBlank
    private String referenceNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(nullable = false, precision = 18, scale = 2)
    @NotNull
    private BigDecimal principal;

    @Column(name = "term_months")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Term termMonths;

    @Column(name = "interest_rate_per_annum", precision = 7, scale = 4)
    @NotNull
    private BigDecimal interestRatePerAnnum;

    @Column(name = "value_date")
    @NotNull
    private Instant valueDate;

    @Column(name = "maturity_date")
    @NotNull
    private Instant maturityDate;

    @ManyToOne
    @JoinColumn(name = "source_account_id", nullable = false)
    @NotNull
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "payout_account_id", nullable = false)
    @NotNull
    private Account payoutAccount;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    @Column(name = "pre_termination_fee_paid", precision = 18, scale = 2)
    private BigDecimal preTerminationFeePaid;

    @Column(name = "is_auto_renew", nullable = false)
    @NotNull
    private boolean isAutoRenew;

    @Transient
    private static final String TD_TYPE_CODE = "TD";

    public TimeDeposit() {
    }

    public TimeDeposit(User user, Term termMonths, Account sourceAccount, Account payoutAccount, BigDecimal principal, boolean isAutoRenew) {
        BigDecimal interestRate;
        switch(termMonths) {
            case THREE_MONTHS:
                interestRate = Term.THREE_MONTHS.getInterestRatePerAnnum();
                break;
            case SIX_MONTHS:
                interestRate = Term.SIX_MONTHS.getInterestRatePerAnnum();
                break;
            case TWELVE_MONTHS:
                interestRate = Term.TWELVE_MONTHS.getInterestRatePerAnnum();
                break;
            default:
                throw new TermDurationException("Invalid term");
        }

        this.user = user;
        this.principal = principal;
        this.termMonths = termMonths;
        this.sourceAccount = sourceAccount;
        this.payoutAccount = payoutAccount;
        this.interestRatePerAnnum = interestRate;
        this.status = Status.ACTIVE;
        this.valueDate = Instant.now();
        this.maturityDate = this.valueDate.atZone(ZoneId.of("Asia/Manila"))
                .plusMonths(termMonths.getMonths())
                .toInstant();
        this.referenceNumber = this.generateReferenceNumber();
        this.isAutoRenew = isAutoRenew;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public String generateReferenceNumber() {
        return Helper.generateAccountNumber(TD_TYPE_CODE, Account.BANK_CODE, Account.BRANCH_CODE, this.valueDate);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Term getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(Term termMonths) {
        this.termMonths = termMonths;
    }

    public BigDecimal getInterestRatePerAnnum() {
        return interestRatePerAnnum;
    }

    public void setInterestRatePerAnnum(BigDecimal interestRatePerAnnum) {
        this.interestRatePerAnnum = interestRatePerAnnum;
    }

    public Instant getValueDate() {
        return valueDate;
    }

    public void setValueDate(Instant valueDate) {
        this.valueDate = valueDate;
    }

    public Instant getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Instant maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getPayoutAccount() {
        return payoutAccount;
    }

    public void setPayoutAccount(Account payoutAccount) {
        this.payoutAccount = payoutAccount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getPreTerminationFeePaid() {
        return preTerminationFeePaid;
    }

    public void setPreTerminationFeePaid(BigDecimal preTerminationFeePaid) {
        this.preTerminationFeePaid = preTerminationFeePaid;
    }

    public boolean isAutoRenew() {
        return isAutoRenew;
    }

    public void setAutoRenew(boolean autoRenew) {
        isAutoRenew = autoRenew;
    }

    @Override
    public String toString() {
        return "TimeDeposit{" +
                "id=" + id +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", user=" + user +
                ", principal=" + principal +
                ", termMonths=" + termMonths +
                ", interestRatePerAnnum=" + interestRatePerAnnum +
                ", valueDate=" + valueDate +
                ", maturityDate=" + maturityDate +
                ", sourceAccount=" + sourceAccount +
                ", payoutAccount=" + payoutAccount +
                ", status=" + status +
                ", preTerminationFeePaid=" + preTerminationFeePaid +
                ", isAutoRenew=" + isAutoRenew +
                '}';
    }
}
