package com.yourplatform.leetcodeclone.dto;

import lombok.Data;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String[] tags; // We'll split the string into an array
    private int votes;
    private int replies; // We'll mock this for now
    private int views;
}