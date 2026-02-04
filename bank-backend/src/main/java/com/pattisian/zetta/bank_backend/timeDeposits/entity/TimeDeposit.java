package com.pattisian.zetta.bank_backend.timeDeposits.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.common.exception.TermDurationException;
import com.pattisian.zetta.bank_backend.common.helpers.Helper;
import com.pattisian.zetta.bank_backend.timeDeposits.enums.Status;
import com.pattisian.zetta.bank_backend.timeDeposits.enums.Term;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
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

    @Column(name = "reference_number", unique = true)
    private String referenceNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(name = "term_months")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Term termMonths;

    @Column(name = "interest_rate_per_annum", precision = 5, scale = 4)
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

    @Transient
    private static final String TD_TYPE_CODE = "TD";

    public TimeDeposit() {
    }

    public TimeDeposit(User user, Term termMonths, Account sourceAccount, Account payoutAccount) {
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
        this.termMonths = termMonths;
        this.sourceAccount = sourceAccount;
        this.payoutAccount = payoutAccount;
        this.interestRatePerAnnum = interestRate;
        this.status = Status.ACTIVE;
        this.valueDate = Instant.now();
        this.maturityDate = this.valueDate.atZone(ZoneId.of("Asia/Manila"))
                .plusYears(3)
                .toInstant();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    @PostPersist
    public void generateReferenceNumber() {
        this.referenceNumber = Helper.generateAccountNumber(this.id, TD_TYPE_CODE, Account.BANK_CODE, Account.BRANCH_CODE, this.valueDate);
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

    @Override
    public String toString() {
        return "TimeDeposit{" +
                "id=" + id +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", user=" + user +
                ", termMonths=" + termMonths +
                ", interestRatePerAnnum=" + interestRatePerAnnum +
                ", valueDate=" + valueDate +
                ", maturityDate=" + maturityDate +
                ", sourceAccount=" + sourceAccount +
                ", payoutAccount=" + payoutAccount +
                ", status=" + status +
                ", preTerminationFeePaid=" + preTerminationFeePaid +
                '}';
    }
}
