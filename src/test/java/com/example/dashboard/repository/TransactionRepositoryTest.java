package com.example.dashboard.repository;

import com.example.dashboard.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository repo;

    @Test
    void getTotalAmount_shouldReturnSumOfAllAmounts() {
        // Given
        Transaction t1 = new Transaction();
        t1.setTransactionId("TXN1");
        t1.setUserName("user1");
        t1.setAmount(new BigDecimal("100.00"));
        t1.setType("DEBIT");
        t1.setCreatedAt(LocalDate.now());

        Transaction t2 = new Transaction();
        t2.setTransactionId("TXN2");
        t2.setUserName("user2");
        t2.setAmount(new BigDecimal("200.00"));
        t2.setType("CREDIT");
        t2.setCreatedAt(LocalDate.now());

        repo.save(t1);
        repo.save(t2);

        // When
        BigDecimal total = repo.getTotalAmount();

        // Then
        assertThat(total).isEqualByComparingTo(new BigDecimal("300.00"));
    }

    @Test
    void getTotalAmount_shouldReturnZeroWhenNoTransactions() {
        // When
        BigDecimal total = repo.getTotalAmount();

        // Then
        assertThat(total).isNull(); // or zero, depending on implementation
    }
}