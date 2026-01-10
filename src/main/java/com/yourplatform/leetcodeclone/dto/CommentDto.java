package com.yourplatform.leetcodeclone.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdAt;

    // Default constructor for JSON
    public CommentDto() {}

    // Constructor for mock data
    public CommentDto(String content, String author) {
        this.content = content;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }
}
