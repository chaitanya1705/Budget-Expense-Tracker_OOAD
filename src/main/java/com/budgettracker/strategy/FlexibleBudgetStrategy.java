package com.budgettracker.strategy;

public class FlexibleBudgetStrategy implements BudgetStrategy {
    @Override
    public boolean isWithinBudget(double spent, double limit) {
        return spent <= limit * 1.1; // Allows 10% flexibility
    }
}