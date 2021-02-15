package com.pibox.swan.service.serviceImpl;

import com.pibox.swan.domain.model.Group;
import com.pibox.swan.domain.model.User;
import com.pibox.swan.repository.GroupRepository;
import com.pibox.swan.repository.UserRepository;
import com.pibox.swan.service.GroupService;
import com.pibox.swan.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserService userService;

    public GroupServiceImpl(GroupRepository groupRepository, UserService userService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
    }

    @Override
    public Group findGroupById(Long id) {
        // TODO:
        return null;
    }

    @Override
    public List<Group> findAllGroupsByUser(User user) {
        // TODO:
        return null;
    }

    @Override
    public Group createNewGroup(User user, String title, String abbreviation, String description, boolean isPublic) {
        Group group = new Group();
        User existsUser = userService.findUserByUsername(user.getUsername());

        group.setCreatedAt(new Date());
        group.setGroupOwner(existsUser);
        group.setTitle(title);
        group.setAbbreviation(abbreviation);
        group.setDescription(description);
        group.setIsPublic(isPublic);
        group.setIsActive(true);
        existsUser.getGroups().add(group);
        groupRepository.save(group);

        return group;
    }

    @Override
    public void addNewGroup(User user, String title, String abbreviation, String description, boolean isPublic, boolean isActive) {
        // TODO:
    }

    @Override
    public void updateGroup(User user, String title, String abbreviation, String description, boolean isPublic, boolean isActive) {
        // TODO:
    }

    @Override
    public void deleteGroupById(Long id) {
        // TODO:
    }
}
