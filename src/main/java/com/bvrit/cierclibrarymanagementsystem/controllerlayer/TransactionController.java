package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.enums.TransactionStatus;
import com.bvrit.cierclibrarymanagementsystem.models.Transaction;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @GetMapping("/get-transaction-list-by-transaction-status-list-and-target-date")
    public ResponseEntity getTransactionListByTransactionStatusListAndDate(@RequestParam List<TransactionStatus> transactionStatusList, @RequestParam LocalDate targetDate){
        return new ResponseEntity(transactionService.getTransactionListByTransactionStatusListAndDate(transactionStatusList, targetDate), HttpStatus.OK);
    }
}
