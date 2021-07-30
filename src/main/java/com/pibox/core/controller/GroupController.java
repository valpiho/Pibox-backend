package com.pibox.core.controller;

import com.pibox.core.domain.model.Group;
import com.pibox.core.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<Group> getAllActivePublicGroups() {
        return groupService.getAllActivePublicGroups();
    }

    @PostMapping("/user-groups")
    public List<Group> getGroupsByUsername(@RequestBody UUID userId) {
        return groupService.getAllGroupsByOwnerId(userId);
    }

    @PostMapping
    public ResponseEntity<Group> createNewGroup(@RequestBody Group group) {
        Group newGroup = groupService.createNewGroup(group.getGroupOwnerId(), group.getTitle(),
                group.getDescription(), group.isPublic());
        return new ResponseEntity<>(newGroup, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable("id") UUID id){
        return groupService.getGroupByGroupId(id);
    }
}
