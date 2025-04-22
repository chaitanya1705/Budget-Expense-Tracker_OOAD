package com.budgettracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.budgettracker.model.Category;
import com.budgettracker.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByCategory(Category category);

    List<Expense> findByCategoryAndDateBetween(Category category, LocalDate startDate, LocalDate endDate);

    List<Expense> findByDateBetween(LocalDate start, LocalDate end);

    List<Expense> findByCategory_Name(String categoryName); // fallback if using String category
}
