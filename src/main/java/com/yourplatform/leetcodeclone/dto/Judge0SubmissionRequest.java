package com.yourplatform.leetcodeclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Judge0SubmissionRequest {

    private String sourceCode;
    private int languageId;
    private String stdin;

    public Judge0SubmissionRequest(String sourceCode, int languageId, String stdin) {
        this.sourceCode = sourceCode;
        this.languageId = languageId;
        this.stdin = stdin;
    }

    // --- ANNOTATIONS ARE NOW ON THE GETTERS ---

    @JsonProperty("source_code")
    public String getSourceCode() {
        return sourceCode;
    }

    @JsonProperty("language_id")
    public int getLanguageId() {
        return languageId;
    }

    @JsonProperty("stdin")
    public String getStdin() {
        return stdin;
    }
}