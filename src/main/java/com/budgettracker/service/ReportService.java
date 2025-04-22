package com.budgettracker.service;

import com.budgettracker.model.Report;
import com.budgettracker.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public void generateReport(String type, String content) {
        Report report = new Report(type, content, LocalDate.now());
        reportRepository.save(report);
    }
}