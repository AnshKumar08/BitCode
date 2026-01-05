package com.yourplatform.leetcodeclone.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Problem problem;

    private String language;

    @Column(columnDefinition = "TEXT")
    private String code;

    private String status = "Pending";  // Pending, Accepted, Wrong Answer, Partial
    private int score = 0;              // 0-100% (passed test cases)

    private LocalDateTime submittedAt = LocalDateTime.now();

    // Default constructor for JPA
    public Submission() {}

    public Submission(Problem problem, String language, String code) {
        this.problem = problem;
        this.language = language;
        this.code = code;
    }
}
