package com.yourplatform.leetcodeclone.dto;

import lombok.Data;

@Data
public class ProblemDto {
    private Long id;
    private String title;
    private String description;
    private String topic;
    private String difficulty;
    private String[] tags;
    private String company;
    private int likes;
    private int discussions;
    private int solved;

    public ProblemDto() {}
}