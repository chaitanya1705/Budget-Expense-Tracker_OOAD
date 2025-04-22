package com.budgettracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.budgettracker.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByUserId(Long userId);

    @Query("SELECT b FROM Bill b WHERE b.dueDate BETWEEN :start AND :end AND b.isPaid = false")
    List<Bill> findUpcomingBills(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT b FROM Bill b WHERE b.dueDate < :today AND b.isPaid = false")
    List<Bill> findOverdueBills(@Param("today") LocalDate today);
}
