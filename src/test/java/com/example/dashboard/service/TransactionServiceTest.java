package com.example.dashboard.service;

import com.example.dashboard.model.Transaction;
import com.example.dashboard.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository repo;

    @InjectMocks
    private TransactionService service;

    @Test
    void saveAll_shouldCallRepositorySaveAll() {
        // Given
        Transaction t1 = new Transaction();
        t1.setTransactionId("TXN1");
        t1.setUserName("user1");
        t1.setAmount(new BigDecimal("100.00"));
        t1.setType("DEBIT");
        t1.setCreatedAt(LocalDate.now());

        List<Transaction> transactions = List.of(t1);

        // When
        service.saveAll(transactions);

        // Then
        verify(repo).saveAll(transactions);
    }
}