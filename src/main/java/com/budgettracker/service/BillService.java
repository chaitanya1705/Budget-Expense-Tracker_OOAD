package com.budgettracker.service;

import com.budgettracker.model.Bill;
import com.budgettracker.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    public Optional<Bill> findById(Long id) {
        return billRepository.findById(id);
    }

    public List<Bill> findByUserId(Long userId) {
        return billRepository.findByUserId(userId);
    }

    public List<Bill> findUpcomingBills(Long userId, int daysAhead) {
        return billRepository.findUpcomingBills(LocalDate.now(), LocalDate.now().plusDays(daysAhead));
    }

    public List<Bill> findOverdueBills(Long userId) {
        return billRepository.findOverdueBills(LocalDate.now());
    }

    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    public void delete(Long id) {
        billRepository.deleteById(id);
    }

    public Bill markAsPaid(Long id) {
        Optional<Bill> billOpt = billRepository.findById(id);
        if (billOpt.isPresent()) {
            Bill bill = billOpt.get();
            bill.markAsPaid(LocalDate.now());

            // If recurring, create the next bill
            if (bill.isRecurring()) {
                LocalDate nextDue = getNextDueDate(bill.getDueDate(), bill.getRecurrenceType());
                Bill next = new Bill();
                next.setName(bill.getName());
                next.setAmount(bill.getAmount());
                next.setDueDate(nextDue);
                next.setRecurring(true);
                next.setRecurrenceType(bill.getRecurrenceType());
                next.setUserId(bill.getUserId());
                next.setCategoryId(bill.getCategoryId());
                billRepository.save(next);
            }

            return billRepository.save(bill);
        } else {
            throw new IllegalArgumentException("Bill not found with ID: " + id);
        }
    }

    private LocalDate getNextDueDate(LocalDate current, Bill.RecurrenceType type) {
        return switch (type) {
            case DAILY -> current.plusDays(1);
            case WEEKLY -> current.plusWeeks(1);
            case BIWEEKLY -> current.plusWeeks(2);
            case MONTHLY -> current.plusMonths(1);
            case QUARTERLY -> current.plusMonths(3);
            case BIANNUAL -> current.plusMonths(6);
            case ANNUAL -> current.plusYears(1);
            default -> current.plusMonths(1);
        };
    }
}
