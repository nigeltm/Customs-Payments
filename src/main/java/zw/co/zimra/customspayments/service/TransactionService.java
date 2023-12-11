package zw.co.zimra.customspayments.service;

import zw.co.zimra.customspayments.dto.Transaction;

import java.util.List;

public interface TransactionService {
    public List<Transaction> getTransactions();
}
