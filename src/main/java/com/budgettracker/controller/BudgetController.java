package com.budgettracker.controller;

import com.budgettracker.model.Budget;
import com.budgettracker.model.BudgetItem;
import com.budgettracker.model.Category;
import com.budgettracker.service.BudgetService;
import com.budgettracker.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/budgets")
    public String budgetPage(Model model) {
        List<Budget> budgets = budgetService.getAllBudgets();
        List<Category> categories = categoryService.getAllCategories();
        
        // Prepare data for the chart
        String chartDataJson = generateChartData(budgets);
        
        // Add data to model
        model.addAttribute("budgets", budgets);
        model.addAttribute("categories", categories);
        model.addAttribute("budget", new Budget());
        model.addAttribute("chartDataJson", chartDataJson);
        
        return "budget";
    }

    @PostMapping("/addBudget")
    public String addBudget(@ModelAttribute Budget budget) {
        budgetService.saveBudget(budget);
        return "redirect:/budgets";
    }

    @PostMapping("/addBudgetItem")
    public String addBudgetItem(@RequestParam Long budgetId, 
                               @RequestParam Long categoryId,
                               @RequestParam BigDecimal amount) {
        budgetService.addOrUpdateBudgetItem(budgetId, categoryId, amount);
        return "redirect:/budgets";
    }

    @GetMapping("/deleteBudgetItem/{id}")
    public String deleteBudgetItem(@PathVariable Long id) {
        budgetService.deleteBudgetItem(id);
        return "redirect:/budgets";
    }
    
    /**
     * Generates chart data in JSON format
     */
    private String generateChartData(List<Budget> budgets) {
        // Map to hold category-wise data
        Map<String, Object> chartData = new HashMap<>();
        List<Map<String, Object>> datasets = new ArrayList<>();
        
        // Get all category names for labels
        Set<String> categories = new HashSet<>();
        
        // Get unique budget names for datasets
        List<String> budgetNames = new ArrayList<>();
        
        // Collect all categories and budget names
        for (Budget budget : budgets) {
            budgetNames.add(budget.getName());
            for (BudgetItem item : budget.getBudgetItems()) {
                if (item.getCategory() != null) {
                    categories.add(item.getCategory().getName());
                }
            }
        }
        
        // Convert to lists for JSON
        chartData.put("labels", new ArrayList<>(categories));
        
        // Generate random colors for each budget
        Random random = new Random();
        
        // Create dataset for each budget
        for (Budget budget : budgets) {
            Map<String, Object> dataset = new HashMap<>();
            dataset.put("label", budget.getName());
            
            // Generate a random color for this budget
            int r = random.nextInt(200);
            int g = random.nextInt(200);
            int b = random.nextInt(200);
            String color = "rgba(" + r + "," + g + "," + b + ",0.6)";
            dataset.put("backgroundColor", color);
            
            // Map of category name to spent amount
            Map<String, BigDecimal> spentByCategory = new HashMap<>();
            
            // Fill with data
            for (BudgetItem item : budget.getBudgetItems()) {
                if (item.getCategory() != null) {
                    spentByCategory.put(item.getCategory().getName(), item.getSpentAmount());
                }
            }
            
            // Convert to list in the same order as labels
            List<BigDecimal> data = ((List<String>)chartData.get("labels")).stream()
                .map(category -> spentByCategory.getOrDefault(category, BigDecimal.ZERO))
                .collect(Collectors.toList());
            
            dataset.put("data", data);
            datasets.add(dataset);
        }
        
        chartData.put("datasets", datasets);
        
        try {
            return new ObjectMapper().writeValueAsString(chartData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}