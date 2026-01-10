package com.yourplatform.leetcodeclone.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class ContestDto {
    private Long id;
    private String title;
    private String description;
    private String selectedTopics;
    private String difficulty;
    private Integer questionCount;
    private Integer durationMinutes;
    private List<Map<String, Object>> problems;
    private String status;
    private Integer score;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
}
