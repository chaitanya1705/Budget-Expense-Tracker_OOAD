package com.budgettracker.observer;

import java.util.ArrayList;
import java.util.List;

public class BudgetNotifier {
    private List<BudgetObserver> observers = new ArrayList<>();

    public void addObserver(BudgetObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(String category, double spent, double limit) {
        for (BudgetObserver observer : observers) {
            observer.update(category, spent, limit);
        }
    }
}