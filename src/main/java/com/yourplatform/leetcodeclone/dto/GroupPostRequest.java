package com.yourplatform.leetcodeclone.dto;

import lombok.Data;

@Data
public class GroupPostRequest {
    private Long authorId;
    private String title;
    private String content;
}


