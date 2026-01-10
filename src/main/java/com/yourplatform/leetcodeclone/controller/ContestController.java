package com.yourplatform.leetcodeclone.controller;

import com.yourplatform.leetcodeclone.dto.ContestDto;
import com.yourplatform.leetcodeclone.model.Contest;
import com.yourplatform.leetcodeclone.model.Problem;
import com.yourplatform.leetcodeclone.repository.ContestRepository;
import com.yourplatform.leetcodeclone.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/contests")
public class ContestController {

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private ProblemRepository problemRepository;

    // ===== GET ALL CONTESTS =====
    @GetMapping
    public List<ContestDto> getAllContests() {
        return contestRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // ===== GET SINGLE CONTEST =====
    @GetMapping("/{id}")
    public ContestDto getContest(@PathVariable Long id) {
        Contest contest = contestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contest not found"));
        return convertToDto(contest);
    }

    // ===== CREATE NEW CONTEST =====
    @PostMapping
    public ResponseEntity<ContestDto> createContest(@RequestBody Map<String, Object> request) {
        String topics = (String) request.get("topics"); // "array,dp,string"
        String difficulty = (String) request.get("difficulty"); // "Mixed"
        Integer questionCount = ((Number) request.get("questionCount")).intValue();
        Integer durationMinutes = ((Number) request.get("durationMinutes")).intValue();

        // Get random problems matching filters
        List<Problem> problems = getRandomProblems(topics, difficulty, questionCount);

        if (problems.size() < questionCount) {
            return ResponseEntity.badRequest().build();
        }

        // Create contest
        Contest contest = new Contest();
        contest.setTitle("Contest - " + topics.replace(",", "/"));
        contest.setDescription("Dynamic contest with " + questionCount + " problems");
        contest.setSelectedTopics(topics);
        contest.setDifficulty(difficulty);
        contest.setQuestionCount(questionCount);
        contest.setDurationMinutes(durationMinutes);
        contest.setProblemIds(problems.stream()
                .map(p -> p.getId().toString())
                .collect(Collectors.joining(",")));
        contest.setCreatedAt(LocalDateTime.now());
        contest.setStatus("DRAFT");
        contest.setUserSolutions("{}");

        Contest saved = contestRepository.save(contest);
        return ResponseEntity.ok(convertToDto(saved));
    }

    // ===== START CONTEST =====
    @PostMapping("/{id}/start")
    public ResponseEntity<ContestDto> startContest(@PathVariable Long id) {
        Contest contest = contestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        contest.setStatus("ONGOING");
        contest.setStartedAt(LocalDateTime.now());
        Contest saved = contestRepository.save(contest);

        return ResponseEntity.ok(convertToDto(saved));
    }

    // ===== SUBMIT SOLUTION =====
    @PostMapping("/{id}/submit")
    public ResponseEntity<ContestDto> submitSolution(@PathVariable Long id, @RequestBody Map<String, String> req) {
        Contest contest = contestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        String problemId = req.get("problemId");
        String code = req.get("code");

        // Simple validation - in real system, execute code
        if (code != null && !code.trim().isEmpty()) {
            contest.setScore(contest.getScore() + 10); // Mock scoring
        }

        Contest saved = contestRepository.save(contest);
        return ResponseEntity.ok(convertToDto(saved));
    }

    // ===== COMPLETE CONTEST =====
    @PostMapping("/{id}/complete")
    public ResponseEntity<ContestDto> completeContest(@PathVariable Long id) {
        Contest contest = contestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        contest.setStatus("COMPLETED");
        contest.setCompletedAt(LocalDateTime.now());
        Contest saved = contestRepository.save(contest);

        return ResponseEntity.ok(convertToDto(saved));
    }

    // ===== HELPERS =====

    private List<Problem> getRandomProblems(String topics, String difficulty, int count) {
        List<Problem> allProblems = problemRepository.findAll();

        return allProblems.stream()
                .filter(p -> {
                    boolean matchTopic = topics.isEmpty() ||
                            Arrays.stream(topics.split(","))
                                    .anyMatch(t -> p.getTags() != null && p.getTags().contains(t));

                    boolean matchDiff = difficulty.equals("Mixed") ||
                            p.getDifficulty().equalsIgnoreCase(difficulty);

                    return matchTopic && matchDiff;
                })
                .sorted((a, b) -> Math.random() > 0.5 ? 1 : -1) // Random shuffle
                .limit(count)
                .collect(Collectors.toList());
    }

    private ContestDto convertToDto(Contest contest) {
        ContestDto dto = new ContestDto();
        dto.setId(contest.getId());
        dto.setTitle(contest.getTitle());
        dto.setDescription(contest.getDescription());
        dto.setSelectedTopics(contest.getSelectedTopics());
        dto.setDifficulty(contest.getDifficulty());
        dto.setQuestionCount(contest.getQuestionCount());
        dto.setDurationMinutes(contest.getDurationMinutes());

        // Get problem details
        if (contest.getProblemIds() != null) {
            List<Long> ids = Arrays.stream(contest.getProblemIds().split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            List<Problem> problems = problemRepository.findAllById(ids);
            dto.setProblems(problems.stream()
                    .map(p -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", p.getId());
                        map.put("title", p.getTitle());
                        map.put("difficulty", p.getDifficulty());
                        return map;
                    })
                    .collect(Collectors.toList()));

        }

        dto.setStatus(contest.getStatus());
        dto.setScore(contest.getScore());
        dto.setCreatedAt(contest.getCreatedAt());
        dto.setStartedAt(contest.getStartedAt());

        return dto;
    }
}
