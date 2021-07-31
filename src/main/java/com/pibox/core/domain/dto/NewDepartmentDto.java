package com.pibox.core.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewDepartmentDto {
    private String title;
    private String description;
    private String country;
    private String city;
    private boolean isPublic;
}
