package com.yourplatform.leetcodeclone.repository;

import com.yourplatform.leetcodeclone.model.GroupComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GroupCommentRepository extends JpaRepository<GroupComment, Long> {
    List<GroupComment> findByPostIdOrderByCreatedAtDesc(Long postId);
}
