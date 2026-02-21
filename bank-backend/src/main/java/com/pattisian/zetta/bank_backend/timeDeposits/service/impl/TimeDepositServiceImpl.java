package com.pattisian.zetta.bank_backend.timeDeposits.service.impl;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.accounts.repository.AccountRepository;
import com.pattisian.zetta.bank_backend.common.ConstantValues;
import com.pattisian.zetta.bank_backend.common.exception.*;
import com.pattisian.zetta.bank_backend.common.helpers.Helper;
import com.pattisian.zetta.bank_backend.security.context.AuthenticatedUserProvider;
import com.pattisian.zetta.bank_backend.timeDeposits.calculation.DocumentaryStampTaxCalculator;
import com.pattisian.zetta.bank_backend.timeDeposits.calculation.MaturityInterestCalculator;
import com.pattisian.zetta.bank_backend.timeDeposits.calculation.PreTerminationInterestEarnedCalculator;
import com.pattisian.zetta.bank_backend.timeDeposits.dto.NewTimeDepositRequestDTO;
import com.pattisian.zetta.bank_backend.timeDeposits.entity.TimeDeposit;
import com.pattisian.zetta.bank_backend.timeDeposits.enums.Status;
import com.pattisian.zetta.bank_backend.timeDeposits.enums.Term;
import com.pattisian.zetta.bank_backend.timeDeposits.repository.TimeDepositRepository;
import com.pattisian.zetta.bank_backend.timeDeposits.service.TimeDepositService;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class TimeDepositServiceImpl implements TimeDepositService {

    private final TimeDepositRepository timeDepositRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final AccountRepository accountRepository;

    public TimeDepositServiceImpl(TimeDepositRepository timeDepositRepository, AuthenticatedUserProvider authenticatedUserProvider, AccountRepository accountRepository) {
        this.timeDepositRepository = timeDepositRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public TimeDeposit openTimeDeposit(NewTimeDepositRequestDTO request) {
        BigDecimal principalAmount = request.getPrincipal();
        if (principalAmount.signum() < 0) {
            throw new NegativeAmountException("Principal amount cannot be negative.");
        }

        if (principalAmount.compareTo(ConstantValues.MIN_TD_PRINCIPAL) < 0) {
            throw new InvalidTdPrincipalAmountException("Principal amount Php" + principalAmount + " is insufficient. Minimum is Php" + ConstantValues.MIN_TD_PRINCIPAL);
        }

        User user = authenticatedUserProvider.getAuthenticatedUser();
        Account sourceAccount = accountRepository.getAccountById(request.getSourceAccountId(), user)
                .orElseThrow(() -> new BankAccountNotFoundException("Source bank account not found."));
        Account payoutAccount = sourceAccount;

        if (!Objects.equals(request.getSourceAccountId(), request.getPayoutAccountId())) {
            payoutAccount = accountRepository.getAccountById(request.getPayoutAccountId(), user)
                    .orElseThrow(() -> new BankAccountNotFoundException("Payout bank account not found."));
        }

        // No Documentary Stamp Tax (DST) - Zetta bank pays this unless customer pre-terminates

        // create a transaction (from sourceAccount)
        TimeDeposit newTimeDeposit = new TimeDeposit(user, request.getTermMonths(), sourceAccount,
                payoutAccount, principalAmount, request.isAutoRenew());

        // Deduct the principalAmount from source account + save account
        sourceAccount.deductAmountFromBalance(request.getPrincipal());
        accountRepository.save(sourceAccount);

        // will have to create a transaction for this

        return timeDepositRepository.save(newTimeDeposit);
    }

    @Transactional
    @Override
    public TimeDeposit preTerminate(Long id) {
        User user = authenticatedUserProvider.getAuthenticatedUser();
        TimeDeposit timeDepositToPreTerminate = timeDepositRepository.getTimeDepositById(user, id)
                .orElseThrow(() -> new TimeDepositNotFoundException("Time deposit with an id " + id + " not found."));

        if (timeDepositToPreTerminate.getStatus() != Status.ACTIVE) {
            throw new TimeDepositNotActiveException("Time deposit with an id " + id + " has already matured or pre-terminated.");
        }
        BigDecimal principal = timeDepositToPreTerminate.getPrincipal();

        // Calculate documentary stamp tax
        BigDecimal dst = DocumentaryStampTaxCalculator.calculate(principal);

        // charge the fees -> need business logic. Penalty fees applied on earned interest rate per unit of time.
        // BigDecimal principalAmount, int termCompletionPercentage, int daysHeld, Term term
        long daysHeld = ChronoUnit.DAYS.between(
                timeDepositToPreTerminate.getValueDate(),
                LocalDate.now(ConstantValues.BANK_ZONE)
        );
        long termCompletionPercentage = 100 * daysHeld / (timeDepositToPreTerminate.getTerm().getMonths() * 30);

        BigDecimal grossInterestEarned = PreTerminationInterestEarnedCalculator.calculate(principal, termCompletionPercentage, daysHeld, timeDepositToPreTerminate.getTerm());

        // subtract withholding tax
        BigDecimal withholdingTax = Helper.getWithholdingTax(grossInterestEarned);
        BigDecimal netInterestEarned = grossInterestEarned
                .subtract(withholdingTax);

        BigDecimal netPayoutAmount = principal
                .subtract(dst)
                .add(netInterestEarned);
        // return remaining to payout account
        Account payoutAccount = timeDepositToPreTerminate.getPayoutAccount();
        payoutAccount.addAmountToBalance(netPayoutAmount);

        timeDepositToPreTerminate.closeTimeDeposit(Status.PRE_TERMINATED, grossInterestEarned, withholdingTax, dst, netInterestEarned, netPayoutAmount);
        timeDepositRepository.save(timeDepositToPreTerminate);
        // update TD - amount, status, etc + save
        // create a tx for this pre-termination -> remaining

        // update payout account - amount transferred + save
        accountRepository.save(payoutAccount);
        // create a transaction + save this payout-> remaining
        return timeDepositToPreTerminate;
    }

    @Override
    public List<TimeDeposit> getAllTimeDeposits() {
        User user = authenticatedUserProvider.getAuthenticatedUser();
        return timeDepositRepository.getAllTimeDepositsByUser(user);
    }

    @Override
    public TimeDeposit getTimeDepositById(Long id) {
        User user = authenticatedUserProvider.getAuthenticatedUser();

        return timeDepositRepository.getTimeDepositById(user, id)
                .orElseThrow(() -> new TimeDepositNotFoundException("Time deposit with an id " + id + " is not found."));
    }

    // scheduled jobs
    private void rollOverTimeDeposits() {
        // check all TD in db where maturity date == today and isAutoRenew == true
        LocalDate today = LocalDate.now(ConstantValues.BANK_ZONE);
        List<TimeDeposit> timeDepositToRollOver = timeDepositRepository.getMaturedTimeDepositToAutoRenew(today);
        // calculate maturityAmount (principal + maturityInterest)
        for (TimeDeposit td : timeDepositToRollOver) {
            // gross maturity interest earned minus withholding tax
            BigDecimal grossMaturityInterestAmount = MaturityInterestCalculator.calculate(td.getPrincipal(), td.getTerm());
            BigDecimal withholdingTax = Helper.getWithholdingTax(grossMaturityInterestAmount);
            BigDecimal netInterestEarned = grossMaturityInterestAmount.subtract(withholdingTax);
            BigDecimal maturityAmount = td.getPrincipal()
                    .add(netInterestEarned);

            TimeDeposit newTdForRollOver = new TimeDeposit(td.getUser(), td.getTerm(), td.getSourceAccount(), td.getPayoutAccount(), maturityAmount, td.isAutoRenew());
            // create tx for new td - sourceAccount still
            timeDepositRepository.save(newTdForRollOver);
            // create tx for closed/matured td - sourceAccount still

            td.closeTimeDeposit(Status.MATURED, grossMaturityInterestAmount, withholdingTax, new BigDecimal("0"), netInterestEarned, maturityAmount);
            timeDepositRepository.save(td);
        }

    }

    // scheduled jobs
    private void terminateMatureTimeDeposits() {
        LocalDate today = LocalDate.now(ConstantValues.BANK_ZONE);
        List<TimeDeposit> maturedTimeDepositToTerminate = timeDepositRepository.getMaturedTimeDeposit(today);

        for (TimeDeposit td : maturedTimeDepositToTerminate) {
            BigDecimal grossMaturityInterestEarned = MaturityInterestCalculator.calculate(td.getPrincipal(), td.getTerm());
            BigDecimal withholdingTax = Helper.getWithholdingTax(grossMaturityInterestEarned);
            BigDecimal netMaturityInterestEarned = grossMaturityInterestEarned.subtract(withholdingTax);
            BigDecimal maturityAmount = td.getPrincipal().add(netMaturityInterestEarned);
            Account payoutAccount = td.getPayoutAccount();
            payoutAccount.addAmountToBalance(maturityAmount);
            // create tx for this
            accountRepository.save(payoutAccount);

            td.closeTimeDeposit(Status.MATURED, grossMaturityInterestEarned, withholdingTax, new BigDecimal("0"), netMaturityInterestEarned, maturityAmount);
            // create tx for this
            timeDepositRepository.save(td);
        }
    }

    @Transactional
    public void processMatureTimeDepositsAndRollOvers() {
        this.rollOverTimeDeposits();
        this.terminateMatureTimeDeposits();
    }


}
