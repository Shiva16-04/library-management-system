package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.repositorylayer.TransactionRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
}
