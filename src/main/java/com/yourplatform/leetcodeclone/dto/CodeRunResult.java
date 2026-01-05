package com.yourplatform.leetcodeclone.dto;

import lombok.Data;

@Data
public class CodeRunResult {
    private String status; // e.g., "Accepted", "Wrong Answer", "Error"
    private String output; // The raw output (stdout)
    private String error; // The error output (stderr)

    private String expectedOutput;
    private String yourOutput;
}