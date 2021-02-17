package com.pibox.swan.repository;

import com.pibox.swan.domain.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    public Group findGroupById(Long id);

    public Group findGroupByGroupId(String groupId);

    public List<Group> findAllByIsActiveIsTrueAndIsPublicIsTrue();
}
