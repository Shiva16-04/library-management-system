package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.TransactionResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.TransactionStatus;
import com.bvrit.cierclibrarymanagementsystem.models.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    public void createTransaction(TransactionStatus transactionStatus, String transactedOn, String transactedBy);
    public List<TransactionResponse> getTransactionListByTransactionStatusListAndDate(List<TransactionStatus>transactionStatusList, LocalDate targetDate);
}
