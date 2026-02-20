package com.pattisian.zetta.bank_backend.timeDeposits.entity;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.common.ConstantValues;
import com.pattisian.zetta.bank_backend.common.helpers.Helper;
import com.pattisian.zetta.bank_backend.timeDeposits.enums.Status;
import com.pattisian.zetta.bank_backend.timeDeposits.enums.Term;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
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
    private Term term;

    @Column(name = "interest_rate_per_annum", precision = 7, scale = 4)
    @NotNull
    private BigDecimal interestRatePerAnnum;

    @Column(name = "value_date")
    @NotNull
    private LocalDate valueDate;

    @Column(name = "maturity_date")
    @NotNull
    private LocalDate maturityDate;

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

    @Column(name = "gross_interest_earned", precision = 18, scale = 2)
    private BigDecimal grossInterestEarned;

    @Column(name = "withholding_tax", precision = 18, scale = 2)
    private BigDecimal withholdingTax;

    @Column(name = "documentary_stamp_tax", precision = 18, scale = 2)
    private BigDecimal documentaryStampTax;

    @Column(name = "net_interest_earned", precision = 18, scale = 2)
    private BigDecimal netInterestEarned;

    @Column(name = "maturity_amount", precision = 18, scale = 2)
    private BigDecimal maturityAmount;


    public TimeDeposit() {
    }

    public TimeDeposit(User user, Term term, Account sourceAccount, Account payoutAccount, BigDecimal principal, boolean isAutoRenew) {
        this.user = user;
        this.principal = principal;
        this.term = term;
        this.sourceAccount = sourceAccount;
        this.payoutAccount = payoutAccount;
        this.interestRatePerAnnum = term.getInterestRatePerAnnum();
        this.status = Status.ACTIVE;
        this.valueDate = LocalDate.now(ConstantValues.BANK_ZONE);
        this.maturityDate = this.valueDate.plusMonths(term.getMonths());
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

    public void closeTimeDeposit(Status status) {
        this.principal = new BigDecimal("0");
        this.status = status;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public String generateReferenceNumber() {
        Instant valueDate = this.valueDate.atStartOfDay(ConstantValues.BANK_ZONE).toInstant();
        return Helper.generateAccountNumber(TD_TYPE_CODE, Account.BANK_CODE, Account.BRANCH_CODE, valueDate);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public BigDecimal getInterestRatePerAnnum() {
        return interestRatePerAnnum;
    }

    public void setInterestRatePerAnnum(BigDecimal interestRatePerAnnum) {
        this.interestRatePerAnnum = interestRatePerAnnum;
    }

    public LocalDate getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
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

    public BigDecimal getGrossInterestEarned() {
        return grossInterestEarned;
    }

    public void setGrossInterestEarned(BigDecimal grossInterestEarned) {
        this.grossInterestEarned = grossInterestEarned;
    }

    public BigDecimal getWithholdingTax() {
        return withholdingTax;
    }

    public void setWithholdingTax(BigDecimal withholdingTax) {
        this.withholdingTax = withholdingTax;
    }

    public BigDecimal getDocumentaryStampTax() {
        return documentaryStampTax;
    }

    public void setDocumentaryStampTax(BigDecimal documentaryStampTax) {
        this.documentaryStampTax = documentaryStampTax;
    }

    public BigDecimal getNetInterestEarned() {
        return netInterestEarned;
    }

    public void setNetInterestEarned(BigDecimal netInterestEarned) {
        this.netInterestEarned = netInterestEarned;
    }

    public BigDecimal getMaturityAmount() {
        return maturityAmount;
    }

    public void setMaturityAmount(BigDecimal maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    @Override
    public String toString() {
        return "TimeDeposit{" +
                "id=" + id +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", user=" + user +
                ", principal=" + principal +
                ", term=" + term +
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
