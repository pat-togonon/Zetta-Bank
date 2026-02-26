package com.pattisian.zetta.bank_backend.transactions.service;

import com.pattisian.zetta.bank_backend.accounts.entity.Account;
import com.pattisian.zetta.bank_backend.transactions.dto.PayBillsRequestDTO;
import com.pattisian.zetta.bank_backend.transactions.entity.Transaction;

public interface TransactionService {

    /*
    createOutgoingTransaction
    createIncomingTransaction
    getAllTransactions - for admin + paginated
    getAllTransactionsByAccountId - for user (user id + account id) + paginated + admin
    getTransactionById - for user (user id + tx id) + admin
    << check remaining methods - scheduled, etc >>
    circle back to account (addNewAccount) - need for initial deposit when opening account (for non-cash deposit)
    circle back to replaceBankCard too - need for initial deposit when opening account (for non-cash deposit)

    Types of Transactions
    If need physical card, check if card is not yet expired. If expired, error - POS Payment (check mechanism first how it calls API) + cash withdrawal
    Synchronize - need one method to only access database at a time

    Transaction Types:

    BILL_PAYMENT,
    INTERNAL_FUND_TRANSFER,
    EXTERNAL_FUND_TRANSFER,
    CASH_WITHDRAWAL,
    INTEREST_CREDIT,
    TIME_DEPOSIT_PAYOUT,
    TIME_DEPOSIT_OPENING,
    TIME_DEPOSIT_PRE_TERMINATION,
    CASH_DEPOSIT,
    INCOMING_WIRE,
    POS_PURCHASE,
    REVERSAL;
     */

   // Transaction payBills(Account account, PayBillsRequestDTO request);








}
