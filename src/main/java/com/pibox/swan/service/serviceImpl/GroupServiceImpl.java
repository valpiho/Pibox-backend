package com.pibox.swan.service.serviceImpl;

import com.pibox.swan.domain.model.Group;
import com.pibox.swan.domain.model.User;
import com.pibox.swan.repository.GroupRepository;
import com.pibox.swan.repository.UserRepository;
import com.pibox.swan.service.GroupService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Group findGroupByGroupId(String groupId) {
        return groupRepository.findGroupByGroupId(groupId);
    }

    @Override
    public List<Group> findAllActivePublicGroups() {
        return groupRepository.findAllByIsActiveIsTrueAndIsPublicIsTrue();
    }

    public List<Group> findAllGroupsByUser(User user) {
        return groupRepository.findAllByUsersIsContaining(user);
    };

    @Override
    public Group createNewGroup(User user, String title, String description, boolean isPublic) {
        Group group = new Group();
        User existsUser = userRepository.findUserByUsername(user.getUsername());

        group.setGroupOwner(existsUser);
        group.setGroupId(generateGroupId());
        group.setCreatedAt(new Date());
        group.setTitle(title);
        group.setDescription(description);
        group.setPublic(isPublic);
        group.setActive(true);

        groupRepository.save(group);
        return group;
    }

    @Override
    public void updateGroup(User user, String title, String description, boolean isPublicGroup, boolean isActiveGroup) {
        // TODO:
    }

    @Override
    public void deleteGroupById(Long id) {
        // TODO:
    }

    private String generateGroupId() {
        return RandomStringUtils.randomNumeric(10);
    }
}
