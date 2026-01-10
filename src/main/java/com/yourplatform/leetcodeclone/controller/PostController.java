package com.yourplatform.leetcodeclone.controller;

import com.yourplatform.leetcodeclone.dto.CommentDto;
import com.yourplatform.leetcodeclone.dto.PostDto;
import com.yourplatform.leetcodeclone.dto.PostRequestDto;
import com.yourplatform.leetcodeclone.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // In‑memory state
    private final Map<Long, List<CommentDto>> commentsStore = new HashMap<>();
    private final Map<Long, PostDto> postsStore = new HashMap<>();
    private final Map<String, Set<Long>> userLikes = new HashMap<>(); // userId → liked postIds
    private final Map<String, Set<Long>> userViews = new HashMap<>(); // userId → viewed postIds

    // ===== LIST + CREATE =====

    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        if (postRequestDto.getTitle() == null || postRequestDto.getTitle().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PostDto savedPost = postService.createPost(postRequestDto);
        postsStore.put(savedPost.getId(), savedPost);
        commentsStore.putIfAbsent(savedPost.getId(), new ArrayList<>());
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    // ===== SINGLE POST =====

    @GetMapping("/{id}")
    public PostDto getPost(@PathVariable Long id) {
        if (postsStore.containsKey(id)) {
            PostDto cached = postsStore.get(id);
            int replies = commentsStore.getOrDefault(id, Collections.emptyList()).size();
            cached.setReplies(replies);
            return cached;
        }

        PostDto post = new PostDto();
        post.setId(id);
        post.setTitle("Two Sum O(n) solution discussion");
        post.setContent("HashMap approach is optimal - store complements!");
        post.setVotes(12);
        int replies = commentsStore.getOrDefault(id, Collections.emptyList()).size();
        post.setReplies(replies);
        post.setViews(150);
        post.setTags(new String[]{"array", "hashmap"});

        postsStore.put(id, post);
        commentsStore.putIfAbsent(id, seedComments());
        return post;
    }

    // ===== COMMENTS =====

    @GetMapping("/{id}/comments")
    public List<CommentDto> getComments(@PathVariable Long id) {
        commentsStore.putIfAbsent(id, seedComments());
        return commentsStore.get(id);
    }

    @PostMapping("/{id}/comments")
    public CommentDto addComment(@PathVariable Long id, @RequestBody Map<String, String> req) {
        String content = req.get("content");
        if (content == null || content.trim().isEmpty()) {
            throw new RuntimeException("Comment content is required");
        }

        CommentDto comment = new CommentDto();
        comment.setId(System.currentTimeMillis());
        comment.setAuthor("You");
        comment.setContent(content.trim());
        comment.setCreatedAt(LocalDateTime.now());

        commentsStore.computeIfAbsent(id, k -> new ArrayList<>()).add(comment);

        PostDto post = postsStore.get(id);
        if (post != null) {
            post.setReplies(commentsStore.get(id).size());
        }

        return comment;
    }

    // ===== LIKES (TOGGLE - LIKE / UNLIKE) =====

    @PostMapping("/{id}/vote")
    public ResponseEntity<?> vote(@RequestParam(defaultValue = "1") Long userId, @PathVariable Long id) {
        String userKey = "user" + userId;
        userLikes.putIfAbsent(userKey, new HashSet<>());

        PostDto post = getPost(id);

        // Check if already liked - toggle it
        if (userLikes.get(userKey).contains(id)) {
            // UNLIKE - decrement votes
            post.setVotes(post.getVotes() - 1);
            userLikes.get(userKey).remove(id);
        } else {
            // LIKE - increment votes
            post.setVotes(post.getVotes() + 1);
            userLikes.get(userKey).add(id);
        }

        return ResponseEntity.ok(post);
    }

    // ===== VIEWS (ONE PER USER ONLY) =====

    @PostMapping("/{id}/view")
    public ResponseEntity<?> view(@RequestParam(defaultValue = "1") Long userId, @PathVariable Long id) {
        String userKey = "user" + userId;
        userViews.putIfAbsent(userKey, new HashSet<>());

        // Check if already viewed
        if (userViews.get(userKey).contains(id)) {
            return ResponseEntity.ok(getPost(id)); // Return post but don't increment
        }

        // Add view and increment views
        PostDto post = getPost(id);
        post.setViews(post.getViews() + 1);
        userViews.get(userKey).add(id);

        return ResponseEntity.ok(post);
    }

    // ===== HELPER =====

    private List<CommentDto> seedComments() {
        List<CommentDto> list = new ArrayList<>();

        CommentDto c1 = new CommentDto();
        c1.setId(System.currentTimeMillis());
        c1.setAuthor("Alice");
        c1.setContent("Great explanation of HashMap approach!");
        c1.setCreatedAt(LocalDateTime.now().minusMinutes(10));
        list.add(c1);

        CommentDto c2 = new CommentDto();
        c2.setId(System.currentTimeMillis() + 1);
        c2.setAuthor("Bob");
        c2.setContent("Brute force is O(n^2) - too slow for interviews");
        c2.setCreatedAt(LocalDateTime.now().minusMinutes(5));
        list.add(c2);

        CommentDto c3 = new CommentDto();
        c3.setId(System.currentTimeMillis() + 2);
        c3.setAuthor("Charlie");
        c3.setContent("Thanks! This helped me pass my FAANG round");
        c3.setCreatedAt(LocalDateTime.now().minusMinutes(2));
        list.add(c3);

        return list;
    }
}
