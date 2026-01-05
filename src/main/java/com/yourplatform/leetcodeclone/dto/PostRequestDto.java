package com.yourplatform.leetcodeclone.dto;

import lombok.Data;

@Data
public class PostRequestDto {
    private String title;
    private String content;
    private String tags;
}