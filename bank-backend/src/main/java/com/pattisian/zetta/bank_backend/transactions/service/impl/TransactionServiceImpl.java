package com.pattisian.zetta.bank_backend.transactions.service.impl;

import com.pattisian.zetta.bank_backend.transactions.repository.TransactionRepository;
import com.pattisian.zetta.bank_backend.transactions.service.AccountTransactionService;
import com.pattisian.zetta.bank_backend.transactions.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountTransactionService accountTransactionService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountTransactionService accountTransactionService) {
        this.transactionRepository = transactionRepository;
        this.accountTransactionService = accountTransactionService;
    }


}
