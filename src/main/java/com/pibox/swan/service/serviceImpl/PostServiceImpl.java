package com.pibox.swan.service.serviceImpl;

import com.pibox.swan.domain.model.*;
import com.pibox.swan.service.GroupService;
import com.pibox.swan.service.PostService;
import com.pibox.swan.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostService postService;
    private final UserService userService;
    private final GroupService groupService;

    public PostServiceImpl(PostService postService, UserService userService, GroupService groupService) {
        this.postService = postService;
        this.userService = userService;
        this.groupService = groupService;
    }

    @Override
    public Post findPostById(Long id) {
        // TODO:
        return null;
    }

    @Override
    public List<Post> findAllPostsByUser(User user) {
        // TODO:
        return null;
    }

    @Override
    public List<Post> findAllPostsByGroup(Group group) {
        // TODO:
        return null;
    }

    @Override
    public List<Post> findAllPostsInGroupByUser(Group group, User user) {
        // TODO:
        return null;
    }

    @Override
    public List<Post> findAllPostsByDepartment(Department department) {
        // TODO:
        return null;
    }

    @Override
    public List<Post> findAllPostsInDepartmentByUser(Department department, User user) {
        // TODO:
        return null;
    }

    @Override
    public List<Post> findAllPostsByClass(Course course) {
        // TODO:
        return null;
    }

    @Override
    public List<Post> findAllPostsInClassByUser(Course course, User user) {
        // TODO:
        return null;
    }

    @Override
    public void addNewPost() {
        // TODO:
    }

    @Override
    public void updatePost() {
        // TODO:
    }

    @Override
    public void deletePost() {
        // TODO:
    }
}
