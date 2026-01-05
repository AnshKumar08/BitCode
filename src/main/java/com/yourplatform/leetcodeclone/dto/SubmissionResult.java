package com.yourplatform.leetcodeclone.dto;

import lombok.Data;

@Data
public class SubmissionResult {
    private Long submissionId;
    private String status;
    private int score;  // 0-100
    private String message;

    public SubmissionResult(Long submissionId, String status, int score) {
        this.submissionId = submissionId;
        this.status = status;
        this.score = score;
    }
}
