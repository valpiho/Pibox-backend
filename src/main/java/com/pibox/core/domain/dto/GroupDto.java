package com.pibox.core.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Date createdAt;
    private Date updatedAt;
    @JsonProperty
    private boolean isPublic;
    @JsonProperty
    private boolean isActive;
}
