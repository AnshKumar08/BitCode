package com.yourplatform.leetcodeclone.repository;

import com.yourplatform.leetcodeclone.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {}