package com.bvrit.cierclibrarymanagementsystem.Transformers;

import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.TransactionResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.TransactionStatus;
import com.bvrit.cierclibrarymanagementsystem.models.Transaction;

public class TransactionTransformer {
    public static Transaction transactionRequestToTransaction(TransactionStatus transactionStatus, String transactedOn, String transactedBy ){
        return Transaction.builder()
                .transactionStatus(transactionStatus)
                .transactedOn(transactedOn)
                .transactedBy(transactedBy)
                .build();
    }
    public static TransactionResponse transactionToTransactionResponse(Transaction transaction){
        return TransactionResponse.builder()
                .creationDate(transaction.getCreationDate())
                .transactionStatus(transaction.getTransactionStatus())
                .transactedOn(transaction.getTransactedOn())
                .transactedBy(transaction.getTransactedBy())
                .build();
    }
}
