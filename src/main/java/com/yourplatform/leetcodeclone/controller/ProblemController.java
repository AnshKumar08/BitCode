package com.yourplatform.leetcodeclone.controller;

import com.yourplatform.leetcodeclone.dto.ProblemDetailDto; // Import
import com.yourplatform.leetcodeclone.dto.ProblemDto;
import com.yourplatform.leetcodeclone.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // Import
import org.springframework.web.bind.annotation.*; // Import
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/problems")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @GetMapping
    public List<ProblemDto> getAllProblems() {
        return problemService.getAllProblems();
    }

    /**
     * NEW ENDPOINT
     * Gets a single problem by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProblemDetailDto> getProblemById(@PathVariable Long id) {
        try {
            ProblemDetailDto problem = problemService.findProblemById(id);
            return ResponseEntity.ok(problem);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}