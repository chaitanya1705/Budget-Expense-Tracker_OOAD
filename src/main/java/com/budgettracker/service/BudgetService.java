package com.budgettracker.service;

import com.budgettracker.model.Budget;
import com.budgettracker.model.BudgetItem;
import com.budgettracker.model.Category;
import com.budgettracker.model.Expense;
import com.budgettracker.repository.BudgetItemRepository;
import com.budgettracker.repository.BudgetRepository;
import com.budgettracker.repository.CategoryRepository;
import com.budgettracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BudgetItemRepository budgetItemRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public Budget saveBudget(Budget budget) {
        if (budget.getBudgetItems() == null) {
            budget.setBudgetItems(new ArrayList<>());
        }
        return budgetRepository.save(budget);
    }

    @Transactional
    public List<Budget> getAllBudgets() {
        List<Budget> budgets = budgetRepository.findAll();
        
        // Force initialization of lazy collections and update spent amounts
        for (Budget budget : budgets) {
            // Initialize budget items collection
            List<BudgetItem> items = budget.getBudgetItems();
            
            for (BudgetItem item : items) {
                // Force initialization of category
                Category category = item.getCategory();
                if (category != null) {
                    // Access the name to ensure it's loaded
                    category.getName();
                }
                
                // Update spent amount
                BigDecimal spent = calculateSpentForItem(budget, item.getCategory());
                item.setSpentAmount(spent);
            }
        }
        
        return budgets;
    }

    @Transactional
    public void addOrUpdateBudgetItem(Long budgetId, Long categoryId, BigDecimal amount) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new IllegalArgumentException("Budget not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        BudgetItem item = budgetItemRepository.findByBudgetAndCategory(budget, category)
                .orElse(new BudgetItem());

        item.setBudget(budget);
        item.setCategory(category);
        item.setAmount(amount);
        item.setSpentAmount(calculateSpentForItem(budget, category));

        // Save the budget item
        BudgetItem savedItem = budgetItemRepository.save(item);
        
        // Make sure the budget has this item
        if (!budget.getBudgetItems().contains(savedItem)) {
            budget.addBudgetItem(savedItem);
            budgetRepository.save(budget);
        }
    }

    @Transactional
    private BigDecimal calculateSpentForItem(Budget budget, Category category) {
        if (budget == null || category == null) {
            return BigDecimal.ZERO;
        }
        
        LocalDate start = LocalDate.of(budget.getYear(), budget.getMonth(), 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Expense> expenses = expenseRepository.findByCategoryAndDateBetween(category, start, end);
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public void deleteBudgetItem(Long id) {
        budgetItemRepository.deleteById(id);
    }
    
    @Transactional
    public Budget getBudgetById(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Budget not found"));
        
        // Force initialization of budget items
        budget.getBudgetItems().size();
        
        // Update spent amounts
        for (BudgetItem item : budget.getBudgetItems()) {
            // Force initialization of category
            if (item.getCategory() != null) {
                item.getCategory().getName();
            }
            
            // Update spent amount
            BigDecimal spent = calculateSpentForItem(budget, item.getCategory());
            item.setSpentAmount(spent);
        }
        
        return budget;
    }
}