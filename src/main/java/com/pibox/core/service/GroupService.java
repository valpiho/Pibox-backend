package com.pibox.core.service;

import com.pibox.core.repository.GroupRepository;
import com.pibox.core.repository.UserRepository;
import com.pibox.core.domain.model.Group;
import com.pibox.core.domain.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.pibox.core.constant.FileConstant.DEFAULT_GROUP_IMAGE_PATH;
import static com.pibox.core.constant.FileConstant.DEFAULT_USER_IMAGE_PATH;

@Service
@Transactional
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Group getGroupByGroupId(UUID groupId) {
        return groupRepository.findGroupByGroupId(groupId);
    }

    public List<Group> getAllActivePublicGroups() {
        return groupRepository.findAllByIsActiveIsTrueAndIsPublicIsTrue();
    }

    public List<Group> getAllGroupsByOwnerId(UUID userId) {
        return groupRepository.findAllByGroupOwnerId(userId);
    };

    public Group createNewGroup(UUID groupOwnerUserId, String title, String description, boolean isPublic) {
        Group group = new Group();
        User user = userRepository.findUserByUserId(groupOwnerUserId);

        group.setGroupOwner(user);
        group.setCreatedAt(new Date());
        group.setTitle(title);
        group.setDescription(description);
        group.setPublic(isPublic);
        group.setActive(true);
        group.setGroupImgUrl(getTemporaryProfileImageUrl(user.getUserId().toString()));
        groupRepository.save(group);
        return group;
    }

    public void updateGroup(User user, String title, String description, boolean isPublicGroup, boolean isActiveGroup) {
        // TODO:
    }

    public void deleteGroupByGroupId(UUID groupId) {
        // TODO:
    }

    private String getTemporaryProfileImageUrl(String groupId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_GROUP_IMAGE_PATH + groupId).toUriString();
    }
}
