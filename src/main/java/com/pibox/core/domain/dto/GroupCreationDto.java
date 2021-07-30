package com.pibox.core.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GroupCreationDto {
    private UUID groupOwnerId;
    private String title;
    private String description;
    private boolean isPublic;
    private boolean isActive;
}
