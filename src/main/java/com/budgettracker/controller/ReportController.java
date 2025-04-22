package com.budgettracker.controller;

import com.budgettracker.model.Budget;
import com.budgettracker.model.BudgetItem;
import com.budgettracker.model.Expense;
import com.budgettracker.service.BudgetService;
import com.budgettracker.service.ExpenseService;
import com.budgettracker.service.ReportService;
import com.budgettracker.util.CsvExporter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private ExpenseService expenseService; // ✅ ADDED this line to resolve error

    @GetMapping("/reports")
    public String getReports(Model model) {
        model.addAttribute("reports", reportService.getAllReports());
        return "report";
    }

    @PostMapping("/generateReport")
    public String generateReport(@RequestParam String type, @RequestParam String content) {
        reportService.generateReport(type, content);
        return "redirect:/reports";
    }

    // ✅ CSV Export Handler
    @GetMapping("/reports/csv")
    public void downloadExpensesCsv(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            HttpServletResponse response) throws IOException {

        List<Expense> expenses = expenseService.getExpensesByDateRange(start, end);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition",
                "attachment; filename=expenses_" + start + "_to_" + end + ".csv");

        CsvExporter.exportExpensesToStream(expenses, response.getWriter());
    }

    // ✅ Show CSV Export UI page
    @GetMapping("/reports/export")
    public String showCsvExportPage() {
        return "report-csv";
    }

    // ✅ PDF Export Handler
    @GetMapping("/reports/pdf")
    public void downloadBudgetReportPdf(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=budget-summary-report.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

        document.add(new Paragraph("Budget Tracker – Category Summary Report", titleFont));
        document.add(new Paragraph("Generated on: " + LocalDate.now(), cellFont));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setWidths(new float[]{3, 3, 2, 2, 2, 2, 2});

        Stream.of("Budget", "Category", "Limit", "Spent", "Remaining", "% Used", "Status").forEach(col -> {
            PdfPCell cell = new PdfPCell(new Phrase(col, headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        });

        List<Budget> budgets = budgetService.getAllBudgets();
        for (Budget budget : budgets) {
            for (BudgetItem item : budget.getBudgetItems()) {
                BigDecimal remaining = item.getAmount().subtract(item.getSpentAmount());
                double percentUsed = item.getAmount().compareTo(BigDecimal.ZERO) > 0
                        ? item.getSpentAmount()
                            .divide(item.getAmount(), 4, BigDecimal.ROUND_HALF_UP)
                            .multiply(BigDecimal.valueOf(100)).doubleValue()
                        : 0.0;
                String status = percentUsed > 100 ? "❌ Exceeded"
                        : (percentUsed > 80 ? "⚠ Near Limit" : "✅ OK");

                table.addCell(new PdfPCell(new Phrase(budget.getName(), cellFont)));
                table.addCell(new PdfPCell(new Phrase(item.getCategory().getName(), cellFont)));
                table.addCell(new PdfPCell(new Phrase(item.getAmount().toString(), cellFont)));
                table.addCell(new PdfPCell(new Phrase(item.getSpentAmount().toString(), cellFont)));
                table.addCell(new PdfPCell(new Phrase(remaining.toString(), cellFont)));
                table.addCell(new PdfPCell(new Phrase(String.format("%.2f%%", percentUsed), cellFont)));
                table.addCell(new PdfPCell(new Phrase(status, cellFont)));
            }
        }

        document.add(table);
        document.close();
    }
}
