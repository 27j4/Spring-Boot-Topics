package com.anshulp.springsecurity.dto;

import com.anshulp.springsecurity.entity.MissionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissionResponse {
    private Long id;
    private String missionName;
    private MissionType missionType;
    private String spaceAgency;
}
