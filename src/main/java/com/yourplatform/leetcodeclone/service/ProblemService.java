package com.yourplatform.leetcodeclone.service;

import com.yourplatform.leetcodeclone.dto.ProblemDetailDto; // Import new DTO
import com.yourplatform.leetcodeclone.dto.ProblemDto;
import com.yourplatform.leetcodeclone.model.Problem;
import com.yourplatform.leetcodeclone.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map; // Import Map
import java.util.stream.Collectors;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    public List<ProblemDto> getAllProblems() {
        return problemRepository.findAll().stream()
                .map(this::convertToListDto) // Renamed for clarity
                .collect(Collectors.toList());
    }

    /**
     * NEW METHOD
     * Fetches a single problem and maps it to the detail DTO.
     */
    public ProblemDetailDto findProblemById(Long id) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found with id: " + id));
        return convertToDetailDto(problem);
    }

    // Mapper for the problem list
    private ProblemDto convertToListDto(Problem problem) {
        ProblemDto dto = new ProblemDto();
        dto.setId(problem.getId());
        dto.setTitle(problem.getTitle());
        dto.setTopic(problem.getTopic());
        dto.setDifficulty(problem.getDifficulty());
        return dto;
    }

    // Mapper for the problem detail page
    private ProblemDetailDto convertToDetailDto(Problem problem) {
        ProblemDetailDto dto = new ProblemDetailDto();
        dto.setId(problem.getId());
        dto.setTitle(problem.getTitle());
        dto.setDescription(problem.getDescription());
        dto.setDifficulty(problem.getDifficulty());

        // Populate the starter code map
        dto.setStarterCode(Map.of(
                "java", problem.getStarterCodeJava(),
                "python", problem.getStarterCodePython(),
                "cpp", problem.getStarterCodeCpp()
        ));
        return dto;
    }
}