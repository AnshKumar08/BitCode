package com.yourplatform.leetcodeclone.controller;

import com.yourplatform.leetcodeclone.repository.ProblemRepository;
import com.yourplatform.leetcodeclone.repository.SubmissionRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    @Autowired
    ProblemRepository problemRepo;
    @Autowired
    SubmissionRepository submissionRepo;

//    @GetMapping
//    public List<UserScore> getLeaderboard() {
//        // Mock top users
//        return List.of(
//                new UserScore("Alice", 2450),
//                new UserScore("Bob", 1987),
//                new UserScore("Charlie", 1678)
//        );
//    }

    @Data
    static class UserScore {
        String username; int score;
        UserScore(String u, int s) { username=u; score=s; }
    }

//    @GetMapping
//    public List<UserScore> getLeaderboard() {
//        // YOUR real data:
//        return submissionRepo.findAll().stream()
//                .collect(Collectors.groupingBy(s->s.getUser().getUsername(),
//                        Collectors.summingInt(s->s.getScore())))
//                .entrySet().stream()
//                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
//                .limit(50)
//                .map(e->new UserScore(e.getKey(), e.getValue()))
//                .collect(Collectors.toList());
//    }

}
