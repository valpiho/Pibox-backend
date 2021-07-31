package com.pibox.core.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class DepartmentDto {
    private UUID departmentId;
    private String title;
    private String description;
    private String country;
    private String city;

    @JsonFormat(timezone = "Europe/Tallinn")
    private Date createdAt;

    @JsonFormat(timezone = "Europe/Tallinn")
    private Date updatedAt;
    private boolean isPublic;
    private boolean isActive;
}
