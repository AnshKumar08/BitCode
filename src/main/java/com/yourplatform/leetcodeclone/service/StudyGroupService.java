package com.yourplatform.leetcodeclone.service;

import com.yourplatform.leetcodeclone.model.*;
import com.yourplatform.leetcodeclone.repository.*;
import com.yourplatform.leetcodeclone.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudyGroupService {

    @Autowired
    private StudyGroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public List<StudyGroup> getMyGroups(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return groupRepository.findAll().stream()
                .filter(group -> group.getMembers().contains(user))
                .collect(Collectors.toList());
    }

    public List<StudyGroup> getAllPublicGroups() {
        return groupRepository.findAllPublic(); // Now this works!
    }

    // ===== GROUP OPERATIONS =====

    public StudyGroup createGroup(CreateGroupRequest request) {
        User creator = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        StudyGroup group = new StudyGroup();
        group.setName(request.getName());
        group.setDescription(request.getDescription());
        group.setCreator(creator);
        group.setMembers(List.of(creator));
        group.setVisibility(request.getVisibility() != null ? request.getVisibility() : "PUBLIC");

        return groupRepository.save(group);
    }

    public StudyGroup getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));
    }

//    public List<StudyGroup> getMyGroups(Long userId) {
//        return groupRepository.findByMemberId(userId);
//    }
//
//    public List<StudyGroup> getAllPublicGroups() {
//        return groupRepository.findAllPublic();
//    }

    public List<StudyGroup> searchGroups(String query) {
        return groupRepository.findByNameContainingIgnoreCase(query);
    }

    public StudyGroup joinGroup(Long groupId, Long userId) {
        StudyGroup group = getGroupById(groupId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (!group.getMembers().contains(user)) {
            group.getMembers().add(user);
            groupRepository.save(group);
        }
        return group;
    }

    public StudyGroup leaveGroup(Long groupId, Long userId) {
        StudyGroup group = getGroupById(groupId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        group.getMembers().remove(user);
        groupRepository.save(group);
        return group;
    }

    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }
}
