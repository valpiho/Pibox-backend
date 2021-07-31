package com.pibox.core.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegistrationDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
}
