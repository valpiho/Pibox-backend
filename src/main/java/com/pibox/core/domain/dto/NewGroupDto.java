package com.pibox.core.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class NewGroupDto {
    private UUID groupOwnerId;
    private String title;
    private String description;
    private boolean isPublic;
}
