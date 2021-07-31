package com.pibox.core.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class GroupDto {

    @JsonProperty(value = "groupId")
    private UUID id;

    private UUID groupOwnerId;
    private String title;
    private String description;
    private String groupImgUrl;

    @JsonFormat(timezone = "Europe/Tallinn")
    private Date createdAt;

    @JsonFormat(timezone = "Europe/Tallinn")
    private Date updatedAt;

    private boolean isPublic;
    private boolean isActive;
}
