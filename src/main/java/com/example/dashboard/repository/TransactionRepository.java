package com.example.dashboard.repository;

import com.example.dashboard.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    @Query("SELECT SUM(t.amount) FROM Transaction t")
    BigDecimal getTotalAmount();
}