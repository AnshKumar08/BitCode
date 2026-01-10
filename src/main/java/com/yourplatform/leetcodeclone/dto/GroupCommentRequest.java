package com.yourplatform.leetcodeclone.dto;

import lombok.Data;

@Data
public class GroupCommentRequest {
    private Long authorId;
    private String content;
    private String codeSnippet;
    private String codeLanguage;
}

