package com.anshulp.springsecurity.dto;

import com.anshulp.springsecurity.entity.MissionType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissionRequest {
    @NotBlank(message = "Mission name is required")
    private String missionName;

    @NotBlank(message = "Mission type is required")
    private MissionType missionType;

    @NotBlank(message = "Space agency is required")
    private String spaceAgency;
}
