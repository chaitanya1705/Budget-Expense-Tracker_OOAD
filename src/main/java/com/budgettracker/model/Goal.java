package com.budgettracker.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goalName;
    private String description;
    private double targetAmount;
    private double savedAmount;
    private LocalDate startDate;
    private LocalDate targetDate;

    public Goal() {}

    public Goal(String goalName, String description, double targetAmount, LocalDate startDate, LocalDate targetDate) {
        this.goalName = goalName;
        this.description = description;
        this.targetAmount = targetAmount;
        this.savedAmount = 0.0;
        this.startDate = startDate;
        this.targetDate = targetDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGoalName() { return goalName; }
    public void setGoalName(String goalName) { this.goalName = goalName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getTargetAmount() { return targetAmount; }
    public void setTargetAmount(double targetAmount) { this.targetAmount = targetAmount; }

    public double getSavedAmount() { return savedAmount; }
    public void setSavedAmount(double savedAmount) { this.savedAmount = savedAmount; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }

    public double getProgressPercentage() {
        if (targetAmount == 0) return 0;
        return (savedAmount / targetAmount) * 100;
    }

    public double getRemainingAmount() {
        return Math.max(0, targetAmount - savedAmount);
    }

    public long getDaysLeft() {
        if (targetDate == null) {
            return 0;
        }
        return LocalDate.now().until(targetDate).getDays();
    }
    
}
