package com.yourplatform.leetcodeclone.service;

import com.yourplatform.leetcodeclone.dto.PostDto;
import com.yourplatform.leetcodeclone.dto.PostRequestDto; // Import new DTO
import com.yourplatform.leetcodeclone.model.Post;
import com.yourplatform.leetcodeclone.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * NEW METHOD
     * Creates a new post, saves it, and returns it as a DTO.
     */
    public PostDto createPost(PostRequestDto postRequestDto) {
        // 1. Create a new Post entity
        Post newPost = new Post();

        // 2. Map data from the DTO to the entity
        newPost.setTitle(postRequestDto.getTitle());
        newPost.setContent(postRequestDto.getContent());
        newPost.setTags(postRequestDto.getTags());

        // Set defaults (votes, views, etc. are already 0 by default)

        // 3. Save the new entity to the database
        Post savedPost = postRepository.save(newPost);

        // 4. Convert the saved entity back to a DTO and return it
        return convertToDto(savedPost);
    }

    // This is your existing private mapper function
    private PostDto convertToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setVotes(post.getVotes());
        dto.setViews(post.getViews());
        dto.setReplies(0); // Placeholder
        if (post.getTags() != null && !post.getTags().isEmpty()) {
            dto.setTags(post.getTags().split(","));
        } else {
            dto.setTags(new String[0]);
        }
        return dto;
    }
}