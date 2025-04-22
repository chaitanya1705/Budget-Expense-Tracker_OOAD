package com.budgettracker.repository;

import com.budgettracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserId(Long userId);

    Category findByName(String name); // Optional helper if needed
}
