package com.pibox.swan.service;

import com.pibox.swan.domain.model.Group;
import com.pibox.swan.domain.model.User;

import java.util.List;

public interface GroupService {

    Group findGroupByGroupId(String groupId);

    List<Group> findAllActivePublicGroups();

    List<Group> findAllGroupsByUser(User user);

    Group createNewGroup(User user, String title, String abbreviation, String description, boolean isPublic);

    void updateGroup(User user, String title, String abbreviation, String description, boolean isPublic, boolean isActive);

    void deleteGroupById(Long id);
}
