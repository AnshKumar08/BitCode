package com.yourplatform.leetcodeclone.repository;

import com.yourplatform.leetcodeclone.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {

    @Query("SELECT sg FROM StudyGroup sg WHERE :userId MEMBER OF sg.members")
    List<StudyGroup> findByMemberId(Long userId);

    @Query("SELECT sg FROM StudyGroup sg WHERE sg.visibility = 'PUBLIC'")
    List<StudyGroup> findAllPublic();

    List<StudyGroup> findByNameContainingIgnoreCase(String name);


}
