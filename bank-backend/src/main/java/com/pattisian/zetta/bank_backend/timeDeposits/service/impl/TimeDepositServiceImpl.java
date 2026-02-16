package com.pattisian.zetta.bank_backend.timeDeposits.service.impl;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.accounts.repository.AccountRepository;
import com.pattisian.zetta.bank_backend.common.exception.BankAccountNotFoundException;
import com.pattisian.zetta.bank_backend.security.context.AuthenticatedUserProvider;
import com.pattisian.zetta.bank_backend.timeDeposits.dto.NewTimeDepositRequestDTO;
import com.pattisian.zetta.bank_backend.timeDeposits.entity.TimeDeposit;
import com.pattisian.zetta.bank_backend.timeDeposits.repository.TimeDepositRepository;
import com.pattisian.zetta.bank_backend.timeDeposits.service.TimeDepositService;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
    public TimeDeposit addTimeDeposit(NewTimeDepositRequestDTO request) {
        User user = authenticatedUserProvider.getAuthenticatedUser();
        Account sourceAccount = accountRepository.getAccountById(request.getSourceAccountId(), user)
                .orElseThrow(() -> new BankAccountNotFoundException("Source bank account not found."));
        Account payoutAccount = sourceAccount;

        if (Objects.equals(request.getSourceAccountId(), request.getPayoutAccountId())) {
            payoutAccount = accountRepository.getAccountById(request.getPayoutAccountId(), user)
                    .orElseThrow(() -> new BankAccountNotFoundException("Payout bank account not found."));
        }

        TimeDeposit newTimeDeposit = new TimeDeposit(user, request.getTermMonths(), sourceAccount,
                payoutAccount, request.getPrincipal(), request.isAutoRenew());

        // Deduct the principal from source account + save account
        sourceAccount.deductAmountFromBalance(request.getPrincipal());
        accountRepository.save(sourceAccount);

        // will have to create a transaction for this

        return timeDepositRepository.save(newTimeDeposit);
    }

    @Transactional
    @Override
    public TimeDeposit preTerminate(Long id) {
        // find TD
        //
        // charge the fees
        // return remaining to payout account
        // update TD - amount, status, etc + save
        // update payout account - amount transferred + save
        // create a transaction + save
        return null;
    }

    @Override
    public List<TimeDeposit> getAllTimeDeposits() {
        return List.of();
    }

    @Override
    public TimeDeposit getTimeDepositById(Long id) {
        return null;
    }
}
