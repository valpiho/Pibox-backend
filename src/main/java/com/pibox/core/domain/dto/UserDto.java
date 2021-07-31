package com.pibox.core.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class UserDto {

    private UUID userId;
    private String firstName;
    private String lastName;
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;
    private String profileImgUrl;

    @JsonFormat(timezone = "Europe/Tallinn")
    private Date joinDate;

    @JsonFormat(timezone = "Europe/Tallinn")
    private Date lastLoginDate;

    private String role;
    private String[] authorities;
    private boolean isActive;
}
