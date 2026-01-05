package com.yourplatform.leetcodeclone.controller;

import com.yourplatform.leetcodeclone.dto.StudyGroupDto;
import com.yourplatform.leetcodeclone.model.StudyGroup;
import com.yourplatform.leetcodeclone.service.StudyGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/groups")
public class StudyGroupController {

    @Autowired
    private StudyGroupService studyGroupService;

    @GetMapping
    public List<StudyGroupDto> getAllGroups() {
        return studyGroupService.findAllGroups();
    }

    @PostMapping
    public ResponseEntity<StudyGroupDto> createGroup(@RequestBody StudyGroupDto studyGroupDto) {
        if (studyGroupDto.getName() == null || studyGroupDto.getName().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        StudyGroupDto savedGroup = studyGroupService.createGroup(studyGroupDto);
        return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    }

    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<StudyGroup> addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        // This endpoint might also be refactored to return a DTO
        // but for now, we'll keep the logic as is.
        try {
            StudyGroup updatedGroup = studyGroupService.addUserToGroup(groupId, userId);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}