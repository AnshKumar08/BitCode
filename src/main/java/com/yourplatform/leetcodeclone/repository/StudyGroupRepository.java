package com.yourplatform.leetcodeclone.repository;

import com.yourplatform.leetcodeclone.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup,Long>{}