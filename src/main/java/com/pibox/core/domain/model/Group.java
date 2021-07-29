package com.pibox.core.domain.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "hobby_groups")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String groupId;
    private String groupOwnerUserId;
    private String title;
    private String description;
    private String groupImgUrl;
    private Date createdAt;
    private Date updatedAt;
    @JsonProperty
    private boolean isPublic;
    @JsonProperty
    private boolean isActive;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Department> departments;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Course> courses;

    @ManyToMany(mappedBy = "groups")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
    @JsonIdentityReference(alwaysAsId=true)
    private Set<User> users;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Post> posts;

    public void setGroupOwner(User user) {
        this.groupOwnerUserId = user.getUserId();
        this.addUser(user);
        user.addGroup(this);
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

    public void addUser(User user) {
        if (this.users == null) {
            this.users = new HashSet<>();
        }
        this.users.add(user);
    }

    public void addPost(Post post) {
        if (this.posts == null) {
            this.posts = new HashSet<>();
        }
        this.posts.add(post);
    }
}
