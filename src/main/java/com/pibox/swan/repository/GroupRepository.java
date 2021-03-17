package com.pibox.swan.repository;

import com.pibox.swan.domain.model.Group;
import com.pibox.swan.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findGroupByGroupId(String groupId);

    List<Group> findAllByIsActiveIsTrueAndIsPublicIsTrue();

    List<Group> findAllByGroupOwnerUserId(String groupOwnerUserId);
}
