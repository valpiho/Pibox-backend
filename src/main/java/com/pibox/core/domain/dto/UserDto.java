package com.pibox.core.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pibox.core.domain.model.Course;
import com.pibox.core.domain.model.Department;
import com.pibox.core.domain.model.Group;
import com.pibox.core.domain.model.Post;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UserDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;
    private String profileImgUrl;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Tallinn")
    private Date joinDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Tallinn")
    private Date lastLoginDate;

    private String role;
    private String[] authorities;
    private boolean isActive;
}
