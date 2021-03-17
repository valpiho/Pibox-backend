package com.pibox.swan.service;

import com.pibox.swan.domain.model.Group;
import com.pibox.swan.domain.model.User;

import java.util.List;

public interface GroupService {

    Group getGroupByGroupId(String groupId);

    List<Group> getAllActivePublicGroups();

    List<Group> getAllGroupsByUserId(String userId);

    Group createNewGroup(String groupOwnerUserId, String title, String description, boolean isPublic);

    void updateGroup(User user, String title, String description, boolean isPublic, boolean isActive);

    void deleteGroupById(Long id);
}
