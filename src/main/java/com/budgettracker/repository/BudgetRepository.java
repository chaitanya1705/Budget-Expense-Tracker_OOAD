package com.budgettracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.budgettracker.model.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByUserIdAndYearAndMonth(Long userId, int year, int month);

    List<Budget> findByUserId(Long userId);
}
