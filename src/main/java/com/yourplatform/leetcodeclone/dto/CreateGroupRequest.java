package com.yourplatform.leetcodeclone.dto;

import lombok.Data;

@Data
public class CreateGroupRequest {
    private Long creatorId;
    private String name;
    private String description;
    private String visibility;
}

