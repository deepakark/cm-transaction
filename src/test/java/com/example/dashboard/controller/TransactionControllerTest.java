package com.example.dashboard.controller;

import com.example.dashboard.model.Transaction;
import com.example.dashboard.repository.TransactionRepository;
import com.example.dashboard.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService service;

    @MockBean
    private TransactionRepository repo;

    @Test
    void uploadPage_shouldReturnUploadView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("upload"));
    }

    @Test
    void uploadCSV_shouldParseAndSaveTransactions() throws Exception {
        // Given
        String csvContent = "transactionId,userName,amount,type,createdAt\nTXN1,user1,100.00,DEBIT,2023-01-01\nTXN2,user2,200.00,CREDIT,2023-01-02";
        MockMultipartFile file = new MockMultipartFile("file", "transactions.csv", "text/csv", csvContent.getBytes());

        // When & Then
        mockMvc.perform(multipart("/upload").file(file))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));

        verify(service).saveAll(anyList());
    }

    @Test
    void dashboard_shouldReturnDashboardViewWithAttributes() throws Exception {
        // Given
        when(repo.count()).thenReturn(2L);
        when(repo.getTotalAmount()).thenReturn(new BigDecimal("300.00"));

        Transaction t1 = new Transaction();
        t1.setId(1L);
        t1.setTransactionId("TXN1");
        t1.setUserName("user1");
        t1.setAmount(new BigDecimal("100.00"));
        t1.setType("DEBIT");
        t1.setCreatedAt(LocalDate.now());

        when(repo.findAll()).thenReturn(List.of(t1));

        // When & Then
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attribute("count", 2L))
                .andExpect(model().attribute("totalAmount", new BigDecimal("300.00")))
                .andExpect(model().attributeExists("transactions"));
    }
}