package com.stylesnap.controllers;

import com.stylesnap.models.Transaction;
import com.stylesnap.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
@RequestMapping("/api/transactions")

public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
