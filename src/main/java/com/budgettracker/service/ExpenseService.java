package com.budgettracker.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budgettracker.model.Category;
import com.budgettracker.model.Expense;
import com.budgettracker.repository.CategoryRepository;
import com.budgettracker.repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense addExpense(String categoryName, BigDecimal amount, String description, LocalDate date) {
        Category category = categoryRepository.findByName(categoryName);
        if (category == null) throw new IllegalArgumentException("Category not found: " + categoryName);

        Expense expense = new Expense(description, amount, date, category.getUserId(), category);
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getExpensesByCategory(String categoryName) {
        return expenseRepository.findByCategory_Name(categoryName);
    }

    public List<Expense> getExpensesByCategoryAndDateRange(String categoryName, LocalDate start, LocalDate end) {
        Category category = categoryRepository.findByName(categoryName);
        return expenseRepository.findByCategoryAndDateBetween(category, start, end);
    }

    public List<Expense> getExpensesByDateRange(LocalDate start, LocalDate end) {
        return expenseRepository.findByDateBetween(start, end);
    }

    public Expense updateExpense(Long id, BigDecimal newAmount, String newDescription) {
        Optional<Expense> optional = expenseRepository.findById(id);
        if (optional.isPresent()) {
            Expense expense = optional.get();
            expense.setAmount(newAmount);
            expense.setDescription(newDescription);
            return expenseRepository.save(expense);
        } else {
            throw new IllegalArgumentException("Expense not found with ID: " + id);
        }
    }
}
