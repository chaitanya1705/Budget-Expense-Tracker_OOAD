package com.budgettracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.budgettracker.model.Budget;
import com.budgettracker.model.BudgetItem;
import com.budgettracker.model.Category;

public interface BudgetItemRepository extends JpaRepository<BudgetItem, Long> {

    Optional<BudgetItem> findByBudgetAndCategory(Budget budget, Category category);

    List<BudgetItem> findByBudget(Budget budget);
}
