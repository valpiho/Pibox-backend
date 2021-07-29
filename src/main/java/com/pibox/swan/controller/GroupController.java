package com.pibox.swan.controller;

import com.pibox.swan.domain.model.Group;
import com.pibox.swan.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Group> getGroupsByUsername(@RequestBody String userId) {
        return groupService.getAllGroupsByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<Group> createNewGroup(@RequestBody Group group) {
        Group newGroup = groupService.createNewGroup(group.getGroupOwnerUserId(), group.getTitle(),
                group.getDescription(), group.isPublic());
        return new ResponseEntity<>(newGroup, HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public Group getGroup(@PathVariable("groupId") String groupId){
        return groupService.getGroupByGroupId(groupId);
    }
}
