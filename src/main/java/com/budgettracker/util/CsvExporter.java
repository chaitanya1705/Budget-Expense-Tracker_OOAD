package com.budgettracker.util;

import com.budgettracker.model.Expense;

import java.io.PrintWriter;
import java.util.List;

public class CsvExporter {
    public static void exportExpensesToStream(List<Expense> expenses, PrintWriter writer) {
        writer.println("Date,Category,Amount,Description");
        for (Expense expense : expenses) {
            String line = String.format("%s,%s,%.2f,%s",
                    expense.getDate(),
                    expense.getCategory().getName(),
                    expense.getAmount(),
                    expense.getDescription().replace(",", " "));
            writer.println(line);
        }
    }
}
