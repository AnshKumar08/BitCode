package com.yourplatform.leetcodeclone.service;

import com.yourplatform.leetcodeclone.model.*;
import com.yourplatform.leetcodeclone.repository.*;
import com.yourplatform.leetcodeclone.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GroupCommentService {

    @Autowired
    private GroupCommentRepository commentRepository;

    @Autowired
    private GroupPostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public GroupComment addComment(Long postId, GroupCommentRequest request) {
        GroupPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        User author = userRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getAuthorId()));

        GroupComment comment = new GroupComment();
        comment.setPost(post);
        comment.setAuthor(author);
        comment.setContent(request.getContent());
        comment.setCodeSnippet(request.getCodeSnippet());
        comment.setCodeLanguage(request.getCodeLanguage() != null ? request.getCodeLanguage() : "java");

        return commentRepository.save(comment);
    }

    public GroupComment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
    }

    public List<GroupComment> getPostComments(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
    }

    public GroupComment likeComment(Long commentId) {
        GroupComment comment = getCommentById(commentId);
        comment.setLikes(comment.getLikes() + 1);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
