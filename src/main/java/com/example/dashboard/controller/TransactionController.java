package com.example.dashboard.controller;

import com.example.dashboard.model.Transaction;
import com.example.dashboard.repository.TransactionRepository;
import com.example.dashboard.service.TransactionService;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TransactionController {

    private final TransactionService service;
    private final TransactionRepository repo;

    public TransactionController(TransactionService service,
                                 TransactionRepository repo) {
        this.service = service;
        this.repo = repo;
    }

    @GetMapping("/")
    public String uploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadCSV(@RequestParam("file") MultipartFile file) throws Exception {

        List<Transaction> list = new ArrayList<>();

        CSVReader reader = new CSVReader(
                new InputStreamReader(file.getInputStream()));

        reader.readNext(); // skip header
        String[] row;

        while ((row = reader.readNext()) != null) {
            Transaction t = new Transaction();
            t.setTransactionId(row[0]);
            t.setUserName(row[1]);
            t.setAmount(new BigDecimal(row[2]));
            t.setType(row[3]);
            t.setCreatedAt(LocalDate.parse(row[4]));
            list.add(t);
        }

        service.saveAll(list);
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("count", repo.count());
        model.addAttribute("totalAmount", repo.getTotalAmount());
        model.addAttribute("transactions", repo.findAll());

        return "dashboard";
    }
}