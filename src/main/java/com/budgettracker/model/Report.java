package com.budgettracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private LocalDate generatedOn;
    private String content;

    public Report() {}

    public Report(String type, String content, LocalDate generatedOn) {
        this.type = type;
        this.content = content;
        this.generatedOn = generatedOn;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public LocalDate getGeneratedOn() { return generatedOn; }
    public void setGeneratedOn(LocalDate generatedOn) { this.generatedOn = generatedOn; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}