package zw.co.zimra.customspayments.service;

import zw.co.zimra.customspayments.dto.ArchivedTransaction;
import zw.co.zimra.customspayments.dto.PageContent;
import zw.co.zimra.customspayments.dto.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    public List<Transaction> getTransactions();

    PageContent getArchivedTransactions(LocalDateTime dateFrom, LocalDateTime dateTo, int page, int size);
}
