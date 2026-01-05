package com.yourplatform.leetcodeclone.controller;

import com.yourplatform.leetcodeclone.dto.CodeRunRequest;
import com.yourplatform.leetcodeclone.dto.CodeRunResult;
import com.yourplatform.leetcodeclone.dto.SubmissionRequest;
import com.yourplatform.leetcodeclone.dto.SubmissionResult;
import com.yourplatform.leetcodeclone.service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CodeExecutionController {

    @Autowired
    private CodeExecutionService codeExecutionService;

    @PostMapping("/run")
    public ResponseEntity<CodeRunResult> runCode(@RequestBody CodeRunRequest runRequest) {

        // --- THIS LOGGING BLOCK IS THE KEY ---
        System.out.println("--- NEW CODE RUN REQUEST ---");
        System.out.println("Received Problem ID: " + (runRequest != null ? runRequest.getProblemId() : "null"));
        System.out.println("Received Language: " + (runRequest != null ? runRequest.getLanguage() : "null"));
        System.out.println("Received Code: " + (runRequest != null ? runRequest.getCode() : "null"));
        System.out.println("-----------------------------");
        // --- END LOGGING BLOCK ---

        CodeRunResult result = codeExecutionService.runCode(runRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/submit")
    public ResponseEntity<SubmissionResult> submitCode(@RequestBody SubmissionRequest request) {
        SubmissionResult result = codeExecutionService.submitCode(request);
        return ResponseEntity.ok(result);
    }

}