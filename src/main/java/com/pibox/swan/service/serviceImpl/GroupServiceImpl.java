package com.pibox.swan.service.serviceImpl;

import com.pibox.swan.domain.model.Group;
import com.pibox.swan.domain.model.User;
import com.pibox.swan.repository.GroupRepository;
import com.pibox.swan.service.GroupService;
import com.pibox.swan.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public void addNewGroup(User user, String title, String abbreviation, String description, boolean isPublic) {
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
