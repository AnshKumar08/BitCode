package com.yourplatform.leetcodeclone.controller;

import com.yourplatform.leetcodeclone.dto.ProblemDetailDto; // Import
import com.yourplatform.leetcodeclone.dto.ProblemDto;
import com.yourplatform.leetcodeclone.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // Import
import org.springframework.web.bind.annotation.*; // Import

import java.util.ArrayList;
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

    // Add this endpoint to YOUR existing ProblemController.java

    @GetMapping("/all")
    public List<ProblemDto> getAllProblemsForExplore() {
        List<ProblemDto> problems = new ArrayList<>();

        // Array problems
        problems.add(createProblem(1L, "Two Sum", "Find two numbers that add up to target", "Easy",
                new String[]{"array", "hash"}, "Google", 156, 45, 8932));
        problems.add(createProblem(2L, "3Sum", "Find all unique triplets that sum to zero", "Medium",
                new String[]{"array", "two-pointer"}, "Amazon", 234, 67, 5421));
        problems.add(createProblem(3L, "Median of Two Sorted Arrays", "Find median in O(log n)", "Hard",
                new String[]{"array", "binary-search"}, "Google", 345, 89, 3210));

        // String problems
        problems.add(createProblem(4L, "Longest Substring", "Find longest substring without repeats", "Medium",
                new String[]{"string", "sliding-window"}, "Meta", 234, 56, 4567));
        problems.add(createProblem(5L, "Valid Parentheses", "Check if parentheses are balanced", "Easy",
                new String[]{"string", "stack"}, "Google", 123, 34, 6789));

        // DP problems
        problems.add(createProblem(6L, "Coin Change", "Minimum coins to make amount", "Medium",
                new String[]{"dp", "greedy"}, "Microsoft", 212, 78, 4123));
        problems.add(createProblem(7L, "House Robber", "Maximum money robbing houses", "Easy",
                new String[]{"dp"}, "Apple", 145, 45, 5432));

        // Graph problems
        problems.add(createProblem(8L, "Number of Islands", "Count connected components", "Medium",
                new String[]{"graph", "dfs"}, "Amazon", 198, 67, 3456));
        problems.add(createProblem(9L, "Network Delay Time", "Shortest path in weighted graph", "Hard",
                new String[]{"graph", "dijkstra"}, "Google", 267, 89, 2345));

        // Tree problems
        problems.add(createProblem(10L, "Binary Tree Traversal", "Traverse tree in order", "Easy",
                new String[]{"tree", "dfs"}, "Meta", 167, 34, 5678));
        problems.add(createProblem(11L, "LCA of BST", "Lowest common ancestor", "Medium",
                new String[]{"tree", "bst"}, "Apple", 201, 56, 4234));

        return problems;
    }

    private ProblemDto createProblem(Long id, String title, String description, String difficulty,
                                     String[] tags, String company, int likes, int discussions, int solved) {
        ProblemDto p = new ProblemDto();
        p.setId(id);
        p.setTitle(title);
        p.setDescription(description);
        p.setDifficulty(difficulty);
        p.setTags(tags);
        p.setCompany(company);
        p.setLikes(likes);
        p.setDiscussions(discussions);
        p.setSolved(solved);
        return p;
    }

}