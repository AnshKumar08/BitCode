package com.yourplatform.leetcodeclone.controller;

import com.yourplatform.leetcodeclone.dto.PostDto;
import com.yourplatform.leetcodeclone.dto.PostRequestDto; // Import new DTO
import com.yourplatform.leetcodeclone.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Import
import org.springframework.http.ResponseEntity; // Import
import org.springframework.web.bind.annotation.*; // Import
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    /**
     * NEW ENDPOINT
     * Listens for POST requests to /api/posts
     */
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        // Basic validation
        if (postRequestDto.getTitle() == null || postRequestDto.getTitle().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Call the service to create the post
        PostDto savedPost = postService.createPost(postRequestDto);

        // Return the saved post DTO with a 201 CREATED status
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }
}