package com.yourplatform.leetcodeclone.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yourplatform.leetcodeclone.dto.*;
import com.yourplatform.leetcodeclone.model.Problem;
import com.yourplatform.leetcodeclone.model.Submission;
import com.yourplatform.leetcodeclone.repository.ProblemRepository;
import com.yourplatform.leetcodeclone.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CodeExecutionService {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SubmissionRepository submissionRepository;

    private static final String JUDGE0_BASE_URL = "https://ce.judge0.com/submissions";

    private static final Map<String, Integer> LANGUAGE_IDS = Map.of(
            "java", 62,
            "python", 71,
            "cpp", 54
    );

    // ============= RUN CODE (Single Test) =============
    public CodeRunResult runCode(CodeRunRequest runRequest) {
        try {
            Problem problem = problemRepository.findById(runRequest.getProblemId())
                    .orElseThrow(() -> new RuntimeException("Problem not found"));

            String stdin = problem.getTestCaseInput();
            String expectedOutput = problem.getTestCaseOutput();

            Integer languageId = LANGUAGE_IDS.get(runRequest.getLanguage().toLowerCase());
            if (languageId == null) {
                return createErrorResult("Unsupported language: " + runRequest.getLanguage());
            }

            Judge0SubmissionRequest submission = new Judge0SubmissionRequest(
                    runRequest.getCode(),
                    languageId,
                    stdin
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Judge0SubmissionRequest> requestEntity = new HttpEntity<>(submission, headers);

            String url = UriComponentsBuilder.fromHttpUrl(JUDGE0_BASE_URL)
                    .queryParam("base64_encoded", "false")
                    .queryParam("wait", "true")
                    .toUriString();

            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> result = response.getBody();
                String stdout = (String) result.get("stdout");
                String stderr = (String) result.get("stderr");

                boolean logicCorrect = outputsMatch(stdout, expectedOutput);

                CodeRunResult codeRunResult = new CodeRunResult();
                codeRunResult.setStatus(logicCorrect ? "Accepted" : "Wrong Answer");
                codeRunResult.setYourOutput(stdout != null ? stdout.trim() : "");
                codeRunResult.setExpectedOutput(expectedOutput.trim());
                codeRunResult.setOutput(stdout != null ? stdout.trim() : "");
                codeRunResult.setError(stderr != null ? stderr.trim() : "");

                return codeRunResult;
            } else {
                return createErrorResult("Judge0 execution failed: " + response.getStatusCode());
            }

        } catch (Exception e) {
            return createErrorResult("Execution error: " + e.getMessage());
        }
    }

    // ============= SUBMIT CODE (Multiple Tests) =============
    public SubmissionResult submitCode(SubmissionRequest request) {
        try {
            Problem problem = problemRepository.findById(request.getProblemId())
                    .orElseThrow(() -> new RuntimeException("Problem not found"));

            Submission submission = new Submission(problem, request.getLanguage(), request.getCode());
            submission.setStatus("Running");
            Submission saved = submissionRepository.save(submission);

            // Run all test cases
            List<TestCaseResult> results = runAllTestCases(request.getCode(), request.getLanguage());
            int passed = (int) results.stream().filter(tcr -> tcr.isPassed()).count();
            int total = results.size();
            int score = (int) ((passed * 100.0) / total);

            // Update verdict
            submission.setScore(score);
            submission.setStatus(passed == total ? "Accepted" :
                    passed > 0 ? "Partial" : "Wrong Answer");
            submissionRepository.save(submission);

            return new SubmissionResult(saved.getId(), submission.getStatus(), score);

        } catch (Exception e) {
            throw new RuntimeException("Submission failed: " + e.getMessage(), e);
        }
    }

    // ============= TEST CASE EXECUTION =============
    private List<TestCaseResult> runAllTestCases(String code, String language) {
        List<TestCase> testCases = List.of(
                new TestCase("[2,7,11,15]\n9", "[0,1]"),
                new TestCase("[3,2,4]\n6", "[1,2]"),
                new TestCase("[3,3]\n6", "[0,1]")
        );

        return testCases.stream()
                .map(tc -> {
                    CodeRunResult result = runSingleTest(code, language, tc.input);
                    boolean passed = outputsMatch(result.getOutput(), tc.expected);
                    return new TestCaseResult(tc, result, passed);
                })
                .collect(Collectors.toList());
    }

    private CodeRunResult runSingleTest(String code, String language, String customInput) {
        try {
            Integer languageId = LANGUAGE_IDS.get(language.toLowerCase());
            if (languageId == null) {
                return createErrorResult("Unsupported language: " + language);
            }

            Judge0SubmissionRequest submission = new Judge0SubmissionRequest(
                    code,
                    languageId,
                    customInput
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Judge0SubmissionRequest> requestEntity = new HttpEntity<>(submission, headers);

            String url = UriComponentsBuilder.fromHttpUrl(JUDGE0_BASE_URL)
                    .queryParam("base64_encoded", "false")
                    .queryParam("wait", "true")
                    .toUriString();

            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> result = response.getBody();
                String stdout = (String) result.get("stdout");
                String stderr = (String) result.get("stderr");

                CodeRunResult codeRunResult = new CodeRunResult();
                codeRunResult.setOutput(stdout != null ? stdout.trim() : "");
                codeRunResult.setError(stderr != null ? stderr.trim() : "");
                codeRunResult.setStatus("Completed");

                return codeRunResult;
            } else {
                return createErrorResult("Judge0 execution failed");
            }

        } catch (Exception e) {
            return createErrorResult("Test execution error: " + e.getMessage());
        }
    }

    // ============= HELPERS =============
    private boolean outputsMatch(String actual, String expected) {
        if (actual == null || expected == null) return false;
        String cleanActual = actual.replaceAll("\\s+", "").trim();
        String cleanExpected = expected.replaceAll("\\s+", "").trim();
        return cleanActual.equals(cleanExpected);
    }

    private CodeRunResult createErrorResult(String message) {
        CodeRunResult result = new CodeRunResult();
        result.setStatus("Error");
        result.setError(message);
        result.setOutput("");
        return result;
    }

    private String getStatusFromId(String statusId) {
        return switch (statusId) {
            case "1" -> "In Queue";
            case "2" -> "Processing";
            case "3" -> "Accepted";
            case "4" -> "Wrong Answer";
            case "5" -> "Time Limit Exceeded";
            case "6" -> "Compilation Error";
            case "7" -> "Runtime Error";
            case "8" -> "Internal Error";
            default -> "Unknown";
        };
    }

    // ============= INNER CLASSES =============
    public static class TestCase {
        public final String input;
        public final String expected;

        public TestCase(String input, String expected) {
            this.input = input;
            this.expected = expected;
        }
    }

    public static class TestCaseResult {
        public final TestCase testCase;
        public final CodeRunResult result;
        private final boolean passed;

        public TestCaseResult(TestCase tc, CodeRunResult r, boolean passed) {
            this.testCase = tc;
            this.result = r;
            this.passed = passed;
        }

        public boolean isPassed() {
            return this.passed;
        }
    }
}
