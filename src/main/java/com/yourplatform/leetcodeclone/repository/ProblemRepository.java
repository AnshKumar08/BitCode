package com.yourplatform.leetcodeclone.repository;

import com.yourplatform.leetcodeclone.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    // Find by difficulty
    List<Problem> findByDifficulty(String difficulty);

    // Find by topic/tags
    List<Problem> findByTagsContaining(String tag);

    // Find by company
    List<Problem> findByCompany(String company);
}
