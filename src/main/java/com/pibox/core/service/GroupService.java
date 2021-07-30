package com.pibox.core.service;

import com.pibox.core.repository.GroupRepository;
import com.pibox.core.repository.UserRepository;
import com.pibox.core.domain.model.Group;
import com.pibox.core.domain.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Group getGroupByGroupId(String groupId) {
        return groupRepository.findGroupByGroupId(groupId);
    }

    public List<Group> getAllActivePublicGroups() {
        return groupRepository.findAllByIsActiveIsTrueAndIsPublicIsTrue();
    }

    public List<Group> getAllGroupsByOwnerId(UUID userId) {
        return groupRepository.findAllByGroupOwnerId(userId);
    };

    public Group createNewGroup(UUID groupOwnerId, String title, String description, boolean isPublic) {
        Group group = new Group();
        User user = userRepository.findUserById(groupOwnerId);

        group.setGroupOwner(user);
        group.setGroupId(generateGroupId());
        group.setCreatedAt(new Date());
        group.setTitle(title);
        group.setDescription(description);
        group.setPublic(isPublic);
        group.setActive(true);

        groupRepository.save(group);
        return group;
    }

    public void updateGroup(User user, String title, String description, boolean isPublicGroup, boolean isActiveGroup) {
        // TODO:
    }

    public void deleteGroupById(Long id) {
        // TODO:
    }

    private String generateGroupId() {
        return RandomStringUtils.randomNumeric(10);
    }
}
