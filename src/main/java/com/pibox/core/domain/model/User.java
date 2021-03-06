package com.pibox.core.domain.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private UUID userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String profileImgUrl;
    private Date joinDate;
    private Date lastLoginDate;
    private String role;
    private String[] authorities;
    private boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_groups",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "groupId"))
    private Set<Group> groups;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_departments",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "department_id", referencedColumnName = "departmentId"))
    private Set<Department> departments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_courses",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    private Set<Course> courses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Post> posts;

    public void addGroup(Group group) {
        if (this.groups == null) {
            this.groups = new HashSet<>();
        }
        this.groups.add(group);
    }

    public void addDepartment(Department department) {
        if (this.departments == null) {
            this.departments = new HashSet<>();
        }
        this.departments.add(department);
    }

    public void addCourse(Course course) {
        if (this.courses == null) {
            this.courses = new HashSet<>();
        }
        this.courses.add(course);
    }

    public void addPost(Post post) {
        if (this.posts == null) {
            this.posts = new HashSet<>();
        }
        this.posts.add(post);
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}


