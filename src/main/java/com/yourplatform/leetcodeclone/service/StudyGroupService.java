package com.yourplatform.leetcodeclone.service;

import com.yourplatform.leetcodeclone.dto.StudyGroupDto;
import com.yourplatform.leetcodeclone.model.StudyGroup;
import com.yourplatform.leetcodeclone.model.User;
import com.yourplatform.leetcodeclone.repository.StudyGroupRepository;
import com.yourplatform.leetcodeclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudyGroupService {

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Autowired
    private UserRepository userRepository;

    public List<StudyGroupDto> findAllGroups() {
        return studyGroupRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public StudyGroupDto createGroup(StudyGroupDto studyGroupDto) {
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName(studyGroupDto.getName());
        studyGroup.setDescription(studyGroupDto.getDescription());

        StudyGroup savedGroup = studyGroupRepository.save(studyGroup);
        return convertToDto(savedGroup);
    }

    @Transactional
    public StudyGroup addUserToGroup(Long groupId, Long userId) {
        StudyGroup group = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        group.getMembers().add(user);
        return studyGroupRepository.save(group);
    }

    // Mapper function
    private StudyGroupDto convertToDto(StudyGroup group) {
        StudyGroupDto dto = new StudyGroupDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());
        return dto;
    }
}