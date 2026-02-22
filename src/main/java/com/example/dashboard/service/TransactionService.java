package com.example.dashboard.service;

import com.example.dashboard.model.Transaction;
import com.example.dashboard.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repo;

    public TransactionService(TransactionRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void saveAll(List<Transaction> transactions) {
        repo.saveAll(transactions);
    }
}