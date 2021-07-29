package com.pibox.core.service;

import com.pibox.core.domain.model.*;
import com.pibox.domain.model.*;
import com.pibox.swan.domain.model.*;

import java.util.List;

public interface PostService {

    Post findPostById(Long id);

    List<Post> findAllPostsByUser(User user);

    List<Post> findAllPostsByGroup(Group group);

    List<Post> findAllPostsInGroupByUser(Group group, User user);

    List<Post> findAllPostsByDepartment(Department department);

    List<Post> findAllPostsInDepartmentByUser(Department department, User user);

    List<Post> findAllPostsByClass(Course course);

    List<Post> findAllPostsInClassByUser(Course course, User user);

    void addNewPost();

    void updatePost();

    void deletePost();
}
