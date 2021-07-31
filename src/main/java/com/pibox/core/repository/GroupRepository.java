package com.pibox.core.repository;

import com.pibox.core.domain.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findGroupByGroupId(UUID id);

    List<Group> findAllByIsActiveIsTrueAndIsPublicIsTrue();

    List<Group> findAllByGroupOwnerId(UUID groupOwnerId);
}
