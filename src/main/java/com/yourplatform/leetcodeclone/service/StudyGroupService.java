package com.yourplatform.leetcodeclone.service;

import com.yourplatform.leetcodeclone.model.StudyGroup;
import com.yourplatform.leetcodeclone.model.User;
import com.yourplatform.leetcodeclone.repository.StudyGroupRepository;
import com.yourplatform.leetcodeclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StudyGroupService {

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Autowired
    private UserRepository userRepository;

    public List<StudyGroup> findAllGroups() {
        return studyGroupRepository.findAll();
    }



    public StudyGroup createGroup(StudyGroup studyGroup) {
        return studyGroupRepository.save(studyGroup);
    }

    @Transactional // Ensures the operation is atomic
    public StudyGroup addUserToGroup(Long groupId, Long userId) {
        // Find the group and user, or throw an exception if not found
        StudyGroup group = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Add user to the group's set of members
        group.getMembers().add(user);

        // Save the updated group
        return studyGroupRepository.save(group);
    }
}