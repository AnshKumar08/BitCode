package com.yourplatform.leetcodeclone.repository;

import com.yourplatform.leetcodeclone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {}