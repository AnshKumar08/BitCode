package com.yourplatform.leetcodeclone.service;

import com.yourplatform.leetcodeclone.model.*;
import com.yourplatform.leetcodeclone.repository.*;
import com.yourplatform.leetcodeclone.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GroupPostService {

    @Autowired
    private GroupPostRepository postRepository;

    @Autowired
    private StudyGroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public GroupPost createPost(Long groupId, GroupPostRequest request) {
        StudyGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));

        User author = userRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getAuthorId()));

        GroupPost post = new GroupPost();
        post.setGroup(group);
        post.setAuthor(author);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        return postRepository.save(post);
    }

    public GroupPost getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
    }

    public List<GroupPost> getGroupPosts(Long groupId) {
        return postRepository.findByGroupIdOrderByCreatedAtDesc(groupId);
    }

    public GroupPost likePost(Long postId) {
        GroupPost post = getPostById(postId);
        post.setLikes(post.getLikes() + 1);
        return postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
