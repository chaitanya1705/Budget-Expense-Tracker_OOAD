package com.budgettracker.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;

@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int month;
    private int year;
    private BigDecimal totalLimit;
    private String description;
    private Long userId;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BudgetItem> budgetItems = new ArrayList<>();

    public Budget() {}

    public Budget(String name, int month, int year, BigDecimal totalLimit, String description, Long userId) {
        this.name = name;
        this.month = month;
        this.year = year;
        this.totalLimit = totalLimit;
        this.description = description;
        this.userId = userId;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public BigDecimal getTotalLimit() { return totalLimit; }
    public void setTotalLimit(BigDecimal totalLimit) { this.totalLimit = totalLimit; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public List<BudgetItem> getBudgetItems() { return budgetItems; }
    public void setBudgetItems(List<BudgetItem> budgetItems) {
        this.budgetItems = budgetItems;
    }

    public YearMonth getYearMonth() {
        return YearMonth.of(year, month);
    }

    public BigDecimal getTotalAllocated() {
        return budgetItems.stream()
                .map(BudgetItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalSpent() {
        return budgetItems.stream()
                .map(BudgetItem::getSpentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getRemainingBudget() {
        return totalLimit.subtract(getTotalSpent());
    }

    public void addBudgetItem(BudgetItem item) {
        item.setBudget(this);
        budgetItems.add(item);
    }
}
