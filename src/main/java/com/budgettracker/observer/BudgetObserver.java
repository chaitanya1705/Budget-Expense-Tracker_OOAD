package com.budgettracker.observer;

public interface BudgetObserver {
    void update(String category, double spent, double limit);
}
