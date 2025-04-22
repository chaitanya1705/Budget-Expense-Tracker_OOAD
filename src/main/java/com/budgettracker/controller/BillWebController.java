package com.budgettracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.budgettracker.model.Bill;
import com.budgettracker.service.BillService;

@Controller
public class BillWebController {

    @Autowired
    private BillService billService;

    @GetMapping("/bills")
    public String showBills(Model model) {
        model.addAttribute("bills", billService.findAll());
        model.addAttribute("bill", new Bill());
        return "bill";
    }

    @PostMapping("/addBill")
    public String addBill(@ModelAttribute Bill bill) {
        billService.save(bill);
        return "redirect:/bills";
    }

    @GetMapping("/markBillPaid/{id}")
    public String markBillPaid(@PathVariable Long id) {
        billService.markAsPaid(id);
        return "redirect:/bills";
    }

    @GetMapping("/deleteBill/{id}")
    public String deleteBill(@PathVariable Long id) {
        billService.delete(id);
        return "redirect:/bills";
    }
}
