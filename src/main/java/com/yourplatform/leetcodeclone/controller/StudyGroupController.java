package com.yourplatform.leetcodeclone.controller;

import com.yourplatform.leetcodeclone.model.*;
import com.yourplatform.leetcodeclone.service.*;
import com.yourplatform.leetcodeclone.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/study-groups")
@CrossOrigin(origins = "*")
public class StudyGroupController {

    @Autowired
    private StudyGroupService groupService;

    @Autowired
    private GroupPostService postService;

    @Autowired
    private GroupCommentService commentService;

    // ===== GROUP CRUD =====

    @PostMapping("/create")
    public ResponseEntity<StudyGroup> createGroup(@RequestBody CreateGroupRequest request) {
        StudyGroup group = groupService.createGroup(request);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/my-groups/{userId}")
    public ResponseEntity<List<StudyGroup>> getMyGroups(@PathVariable Long userId) {
        List<StudyGroup> groups = groupService.getMyGroups(userId);
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/all-public")
    public ResponseEntity<List<StudyGroup>> getAllPublicGroups() {
        List<StudyGroup> groups = groupService.getAllPublicGroups();
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/search")
    public ResponseEntity<List<StudyGroup>> searchGroups(@RequestParam String query) {
        List<StudyGroup> groups = groupService.searchGroups(query);
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<StudyGroup> getGroup(@PathVariable Long groupId) {
        StudyGroup group = groupService.getGroupById(groupId);
        return ResponseEntity.ok(group);
    }

    @PostMapping("/{groupId}/join")
    public ResponseEntity<StudyGroup> joinGroup(@PathVariable Long groupId, @RequestParam Long userId) {
        StudyGroup group = groupService.joinGroup(groupId, userId);
        return ResponseEntity.ok(group);
    }

    @DeleteMapping("/{groupId}/leave")
    public ResponseEntity<String> leaveGroup(@PathVariable Long groupId, @RequestParam Long userId) {
        groupService.leaveGroup(groupId, userId);
        return ResponseEntity.ok("Left group successfully");
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.ok("Group deleted successfully");
    }

    // ===== GROUP POSTS =====

    @PostMapping("/{groupId}/posts")
    public ResponseEntity<GroupPost> createPost(@PathVariable Long groupId, @RequestBody GroupPostRequest request) {
        GroupPost post = postService.createPost(groupId, request);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/{groupId}/posts")
    public ResponseEntity<List<GroupPost>> getGroupPosts(@PathVariable Long groupId) {
        List<GroupPost> posts = postService.getGroupPosts(groupId);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<GroupPost> likePost(@PathVariable Long postId) {
        GroupPost post = postService.likePost(postId);
        return ResponseEntity.ok(post);
    }

    // ===== COMMENTS =====

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<GroupComment> addComment(@PathVariable Long postId, @RequestBody GroupCommentRequest request) {
        GroupComment comment = commentService.addComment(postId, request);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<GroupComment>> getComments(@PathVariable Long postId) {
        List<GroupComment> comments = commentService.getPostComments(postId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }

    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<GroupComment> likeComment(@PathVariable Long commentId) {
        GroupComment comment = commentService.likeComment(commentId);
        return ResponseEntity.ok(comment);
    }
}
