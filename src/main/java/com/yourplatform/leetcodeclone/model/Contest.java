package com.yourplatform.leetcodeclone.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(columnDefinition = "TEXT")
    private String selectedTopics; // comma-separated: "array,dp,string"

    private String difficulty; // "Easy", "Medium", "Hard", "Mixed"
    private Integer questionCount; // 3, 5, 10
    private Integer durationMinutes; // 30, 60, 120

    @Column(columnDefinition = "TEXT")
    private String problemIds; // comma-separated IDs of selected problems

    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    private String status; // "DRAFT", "ONGOING", "COMPLETED"

    @Column(columnDefinition = "TEXT")
    private String userSolutions; // JSON: {problemId: code, ...}

    private Integer score = 0;
}
