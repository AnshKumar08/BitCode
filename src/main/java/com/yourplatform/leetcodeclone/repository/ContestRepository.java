package com.yourplatform.leetcodeclone.repository;

import com.yourplatform.leetcodeclone.model.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {
    // Find all contests by status
    List<Contest> findByStatus(String status);

    // Find all contests ordered by creation date
    List<Contest> findAllByOrderByCreatedAtDesc();
}
