package com.budgettracker.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budgettracker.model.Goal;
import com.budgettracker.repository.GoalRepository;

@Service
public class GoalService {
    @Autowired
    private GoalRepository goalRepository;

    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    public void addGoal(Goal goal) {
        // If no dates are provided, set default ones
        if (goal.getStartDate() == null) {
            goal.setStartDate(LocalDate.now());
        }
        if (goal.getTargetDate() == null) {
            goal.setTargetDate(LocalDate.now().plusMonths(6));
        }
        goalRepository.save(goal);
    }

    public void deleteGoal(Long id) {
        goalRepository.deleteById(id);
    }

    public void updateSavedAmount(Long id, double amount) {
        Goal goal = goalRepository.findById(id).orElse(null);
        if (goal != null) {
            goal.setSavedAmount(goal.getSavedAmount() + amount);
            goalRepository.save(goal);
        }
    }
}
