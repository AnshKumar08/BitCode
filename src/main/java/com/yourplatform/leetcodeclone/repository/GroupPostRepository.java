package com.yourplatform.leetcodeclone.repository;

import com.yourplatform.leetcodeclone.model.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GroupPostRepository extends JpaRepository<GroupPost, Long> {
    List<GroupPost> findByGroupIdOrderByCreatedAtDesc(Long groupId);
}
