// src/main/java/com/yourplatform/leetcodeclone/model/User.java
package com.yourplatform.leetcodeclone.model;

import com.fasterxml.jackson.annotation.JsonBackReference; // <-- Import this
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(mappedBy = "members")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference // <-- Add this annotation
    private Set<StudyGroup> studyGroups = new HashSet<>();
}