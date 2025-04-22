package com.budgettracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.budgettracker.model.Goal;
import com.budgettracker.service.GoalService;

@Controller
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping("/goals")
    public String viewGoalsPage(Model model) {
        List<Goal> goals = goalService.getAllGoals();
        model.addAttribute("goals", goals);
        model.addAttribute("goal", new Goal());
        return "goal";
    }

    @PostMapping("/addGoal")
    public String addGoal(@ModelAttribute Goal goal) {
        if (goal.getStartDate() == null) {
            goal.setStartDate(LocalDate.now());
        }
        if (goal.getTargetDate() == null) {
            goal.setTargetDate(LocalDate.now().plusMonths(6));
        }
        
        goalService.addGoal(goal);
        return "redirect:/goals";
    }

    @PostMapping("/updateSaved/{id}")
    public String updateSavedAmount(@PathVariable Long id, @RequestParam double amount) {
        goalService.updateSavedAmount(id, amount);
        return "redirect:/goals";
    }

    @GetMapping("/deleteGoal/{id}")
    public String deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return "redirect:/goals";
    }
}
