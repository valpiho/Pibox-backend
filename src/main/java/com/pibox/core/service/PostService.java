package com.pibox.core.service;

import com.pibox.core.domain.model.*;
import com.pibox.core.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final GroupService groupService;

    public PostService(PostRepository postRepository, UserService userService, GroupService groupService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.groupService = groupService;
    }

    public Post findPostById(Long id) {
        // TODO:
        return null;
    }

    public List<Post> findAllPostsByUser(User user) {
        // TODO:
        return null;
    }

    public List<Post> findAllPostsByGroup(Group group) {
        // TODO:
        return null;
    }

    public List<Post> findAllPostsInGroupByUser(Group group, User user) {
        // TODO:
        return null;
    }

    public List<Post> findAllPostsByDepartment(Department department) {
        // TODO:
        return null;
    }

    public List<Post> findAllPostsInDepartmentByUser(Department department, User user) {
        // TODO:
        return null;
    }

    public List<Post> findAllPostsByClass(Course course) {
        // TODO:
        return null;
    }

    public List<Post> findAllPostsInClassByUser(Course course, User user) {
        // TODO:
        return null;
    }

    public void addNewPost() {
        // TODO:
    }

    public void updatePost() {
        // TODO:
    }

    public void deletePost() {
        // TODO:
    }
}
