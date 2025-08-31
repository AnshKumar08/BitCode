// src/main/java/com/yourplatform/leetcodeclone/model/StudyGroup.java
package com.yourplatform.leetcodeclone.model;

import com.fasterxml.jackson.annotation.JsonManagedReference; // <-- Import this
import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class StudyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonManagedReference // <-- Add this annotation
    private Set<User> members = new HashSet<>();
}