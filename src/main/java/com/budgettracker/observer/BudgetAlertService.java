package com.budgettracker.observer;

public class BudgetAlertService implements BudgetObserver {
    @Override
    public void update(String category, double spent, double limit) {
        if (spent > limit) {
            System.out.println("[ALERT] Budget exceeded for category: " + category);
        }
    }
}