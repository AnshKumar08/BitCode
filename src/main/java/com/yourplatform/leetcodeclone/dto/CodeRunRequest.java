package com.yourplatform.leetcodeclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// We are replacing @Data with explicit methods to be 100% sure
public class CodeRunRequest {

    @JsonProperty("problemId")
    private Long problemId;

    @JsonProperty("language")
    private String language;

    @JsonProperty("code")
    private String code;

    // A default constructor is required for JSON deserialization
    public CodeRunRequest() {}

    // --- Getters ---
    public Long getProblemId() {
        return problemId;
    }

    public String getLanguage() {
        return language;
    }

    public String getCode() {
        return code;
    }

    // --- Setters ---
    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCode(String code) {
        this.code = code;
    }
}