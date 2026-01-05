package com.yourplatform.leetcodeclone.dto;

import lombok.Data;

@Data
public class ProblemDto {
    private Long id;
    private String title;
    private String topic;
    private String difficulty;
}