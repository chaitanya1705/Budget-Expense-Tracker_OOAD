package com.budgettracker.strategy;

public class StrictBudgetStrategy implements BudgetStrategy {
    @Override
    public boolean isWithinBudget(double spent, double limit) {
        return spent <= limit;
    }
}