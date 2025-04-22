package com.budgettracker.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.budgettracker.model.Expense;
import com.budgettracker.repository.ExpenseRepository;

@Controller
public class HomeController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/export-expenses")
    public ResponseEntity<InputStreamResource> exportExpensesToCsv() {
        List<Expense> expenses = expenseRepository.findAll();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);
        writer.println("Date,Category,Amount,Description");
        for (Expense e : expenses) {
            writer.println(String.format("%s,%s,%.2f,%s", e.getDate(), e.getCategory(), e.getAmount(), e.getDescription()));
        }
        writer.flush();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=expenses.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(inputStream));
    }
}