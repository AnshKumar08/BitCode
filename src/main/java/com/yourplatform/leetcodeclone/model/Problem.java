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

    // --- TEST CASES ---
    @Column(columnDefinition = "TEXT")
    private String testCaseInput; // e.g., "[2,7,11,15]\n9"

    @Column(columnDefinition = "TEXT")
    private String testCaseOutput; // e.g., "[0, 1]"

    // --- EXPLORE PAGE FIELDS (NEW) ---
    @Column(columnDefinition = "TEXT")
    private String tags; // comma-separated: "array,hash,two-pointer"

    private String company; // "Google", "Amazon", "Meta", etc.

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer likes = 0;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer discussions = 0;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer solved = 0;
}
