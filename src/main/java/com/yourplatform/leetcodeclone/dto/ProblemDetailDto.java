package com.yourplatform.leetcodeclone.dto;

import lombok.Data;
import java.util.Map;

@Data
public class ProblemDetailDto {
    private Long id;
    private String title;
    private String description;
    private String difficulty;
    // We'll use a Map to hold the starter code for different languages
    private Map<String, String> starterCode;
}