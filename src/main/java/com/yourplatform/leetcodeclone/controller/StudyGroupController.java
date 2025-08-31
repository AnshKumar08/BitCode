package com.yourplatform.leetcodeclone.controller;

import com.yourplatform.leetcodeclone.model.StudyGroup;
import com.yourplatform.leetcodeclone.service.StudyGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class StudyGroupController {

    @Autowired
    private StudyGroupService studyGroupService;

    @GetMapping
    public List<StudyGroup> getAllGroups() {
        return studyGroupService.findAllGroups();
    }

    @PostMapping
    public ResponseEntity<StudyGroup> createGroup(@RequestBody StudyGroup studyGroup) {
        if (studyGroup.getName() == null || studyGroup.getName().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        StudyGroup savedGroup = studyGroupService.createGroup(studyGroup);
        return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    }

    // New endpoint to add a user to a group
    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<StudyGroup> addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        try {
            StudyGroup updatedGroup = studyGroupService.addUserToGroup(groupId, userId);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}