package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.TransactionTransformer;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.TransactionResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.TransactionStatus;
import com.bvrit.cierclibrarymanagementsystem.models.Transaction;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.TransactionRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public void createTransaction(TransactionStatus transactionStatus, String transactedOn, String transactedBy) {
        Transaction transaction= TransactionTransformer.transactionRequestToTransaction(transactionStatus, transactedOn, transactedBy);
        transactionRepository.save(transaction);
    }
    public List<TransactionResponse> getTransactionListByTransactionStatusListAndDate(List<TransactionStatus>transactionStatusList, LocalDate targetDate){
        List<Transaction>transactionList=transactionRepository.findByTransactionStatusInAndCreationDate(transactionStatusList, targetDate);
        List<TransactionResponse>transactionResponseList=new ArrayList<>();
        for(Transaction transaction: transactionList){
            TransactionResponse transactionResponse=TransactionTransformer.transactionToTransactionResponse(transaction);
            transactionResponseList.add(transactionResponse);
        }
        return transactionResponseList;
    }
}
