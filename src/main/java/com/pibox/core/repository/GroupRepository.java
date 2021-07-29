package com.pibox.core.repository;

import com.pibox.core.domain.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findGroupByGroupId(String groupId);

    List<Group> findAllByIsActiveIsTrueAndIsPublicIsTrue();

    List<Group> findAllByGroupOwnerUserId(String groupOwnerUserId);
}
