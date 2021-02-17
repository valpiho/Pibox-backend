package com.pibox.swan.controller;

import com.pibox.swan.domain.model.Group;
import com.pibox.swan.domain.model.User;
import com.pibox.swan.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/")
    public List<Group> getAllActivePublicGroups() {
        return groupService.findAllActivePublicGroups();
    }

    @GetMapping("/my_groups")
    public List<Group> getGroupsByUser(@RequestBody User user) {
        return groupService.findAllGroupsByUser(user);
    }

    @PostMapping("/create")
    public ResponseEntity<Group> createNewGroup(@RequestBody Group group) {
        Group newGroup = groupService.createNewGroup(group.getGroupOwner(), group.getTitle(), group.getAbbreviation(), group.getDescription(), group.getIsPublic());
        return new ResponseEntity<>(newGroup, HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public Group getGroup(@PathVariable("groupId") String groupId){
        return groupService.findGroupByGroupId(groupId);
    }


}