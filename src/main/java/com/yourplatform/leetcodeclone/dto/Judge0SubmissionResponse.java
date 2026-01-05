package com.yourplatform.leetcodeclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Judge0SubmissionResponse {

    private String token;

    // Default constructor for Jackson
    public Judge0SubmissionResponse() {}

    // Getter
    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    // Setter
    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
    }
}