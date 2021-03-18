package com.pibox.swan.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String departmentId;
    private String title;
    private String description;
    private String country; // TODO: Enum ???? Default inherits from Group
    private String city; // TODO: Enum ???? Default inherits from Group
    private Date createdAt;
    private Date updatedAt;
    @JsonProperty
    private boolean isPublic;
    @JsonProperty
    private boolean isActive;

    @ManyToOne()
    @JoinColumn(name = "group_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "groupId")
    @JsonIdentityReference(alwaysAsId=true)
    private Group group;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Course> courses;

    @ManyToMany(mappedBy = "departments")
    private Set<User> users;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Post> posts;

    public void setGroup(Group group) {
        this.group = group;
        group.addDepartment(this);
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
