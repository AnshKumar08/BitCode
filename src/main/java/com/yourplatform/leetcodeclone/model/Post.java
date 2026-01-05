package com.yourplatform.leetcodeclone.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String tags; // Simple comma-separated string for now
    private int votes = 0;
    private int views = 0;

    @Column(columnDefinition = "TEXT")
    private String content; // For the main post body

    private LocalDateTime createdAt = LocalDateTime.now();

    // In a real app, you'd link this to a User
    // @ManyToOne
    // private User author;
}