package com.pibox.core.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class GroupDto {
    private UUID id;
    private UUID groupOwnerId;
    private String title;
    private String description;
    private String groupImgUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Europe/Tallinn")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Europe/Tallinn")
    private Date updatedAt;

    private boolean isPublic;
    private boolean isActive;
}
