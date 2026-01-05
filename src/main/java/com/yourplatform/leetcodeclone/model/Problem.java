package com.yourplatform.leetcodeclone.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String topic;
    private String difficulty;

    @Column(columnDefinition = "TEXT")
    private String starterCodeJava;

    @Column(columnDefinition = "TEXT")
    private String starterCodePython;

    @Column(columnDefinition = "TEXT")
    private String starterCodeCpp;

    // --- NEW FIELDS ---
    @Column(columnDefinition = "TEXT")
    private String testCaseInput; // e.g., "[2,7,11,15]\n9" (for Two Sum)

    @Column(columnDefinition = "TEXT")
    private String testCaseOutput; // e.g., "[0, 1]" (the expected answer)
}