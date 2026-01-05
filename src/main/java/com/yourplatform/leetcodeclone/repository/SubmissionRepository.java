package com.yourplatform.leetcodeclone.repository;

import com.yourplatform.leetcodeclone.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
