package com.yourplatform.leetcodeclone.controller;

import com.yourplatform.leetcodeclone.model.Problem;
import com.yourplatform.leetcodeclone.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {

    @Autowired
    private ProblemRepository problemRepository;

    @GetMapping
    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }
}