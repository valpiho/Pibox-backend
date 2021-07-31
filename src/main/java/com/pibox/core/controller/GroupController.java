package com.pibox.core.controller;

import com.pibox.core.domain.dto.NewGroupDto;
import com.pibox.core.domain.dto.GroupDto;
import com.pibox.core.domain.model.Group;
import com.pibox.core.mapper.GroupMapper;
import com.pibox.core.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/groups")
public class GroupController {

    private final GroupService groupService;
    private final GroupMapper groupMapper;

    public GroupController(GroupService groupService,
                           GroupMapper groupMapper) {
        this.groupService = groupService;
        this.groupMapper = groupMapper;
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable("groupId") UUID groupId){
        Group group = groupService.getGroupByGroupId(groupId);
        return new ResponseEntity<>( groupMapper.toGroupDto(group), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllActivePublicGroups() {
        List<Group> groups = groupService.getAllActivePublicGroups();
        List<GroupDto> groupsDtoList = groups.stream().map(groupMapper::toGroupDto).collect(Collectors.toList());
        return new ResponseEntity<>(groupsDtoList, HttpStatus.OK);
    }

    @GetMapping("/user-groups/{userId}")
    public ResponseEntity<List<GroupDto>> getGroupsByUsername(@PathVariable("userId") UUID userId) {
        List<Group> groups = groupService.getAllGroupsByOwnerId(userId);
        List<GroupDto> groupsDtoList = groups.stream().map(groupMapper::toGroupDto).collect(Collectors.toList());
        return new ResponseEntity<>(groupsDtoList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GroupDto> createNewGroup(@RequestBody NewGroupDto group) {
        Group newGroup = groupService.createNewGroup(group.getGroupOwnerId(), group.getTitle(),
                group.getDescription(), group.isPublic());
        return new ResponseEntity<>(groupMapper.toGroupDto(newGroup), HttpStatus.OK);
    }
}
