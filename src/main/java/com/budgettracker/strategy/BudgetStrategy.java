package com.budgettracker.strategy;

public interface BudgetStrategy {
    boolean isWithinBudget(double spent, double limit);
}