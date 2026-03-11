package com.anshulp.springsecurity.service;

import com.anshulp.springsecurity.dto.MissionRequest;
import com.anshulp.springsecurity.dto.MissionResponse;
import com.anshulp.springsecurity.entity.Mission;
import com.anshulp.springsecurity.entity.User;
import com.anshulp.springsecurity.error.ForbiddenException;
import com.anshulp.springsecurity.error.ResourceNotFoundException;
import com.anshulp.springsecurity.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final MissionRepository missionRepository;

    private MissionResponse maptoMissionResponse(Mission mission) {
        MissionResponse response = new MissionResponse();
        response.setId(mission.getId());
        response.setMissionName(mission.getMissionName());
        response.setMissionType(mission.getMissionType());
        response.setSpaceAgency(mission.getSpaceAgency());
        return response;
    }

    @PreAuthorize("hasAuthority('MISSION_CREATE')")
    public MissionResponse createOrder(MissionRequest missionRequest, User user) {
        Mission mission = new Mission();
        mission.setMissionName(missionRequest.getMissionName());
        mission.setMissionType(missionRequest.getMissionType());
        mission.setSpaceAgency(missionRequest.getSpaceAgency());
        mission.setUser(user);
        missionRepository.save(mission);
        return maptoMissionResponse(mission);
    }

    @PreAuthorize("hasAuthority('MISSION_DELETE')")
    public String deleteOrder(Long missionId, User user) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new ResourceNotFoundException("Mission not found with id: " + missionId));
        if (!mission.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("You are not authorized to delete this mission");
        }
        missionRepository.delete(mission);
        return "Mission deleted successfully";
    }

    @PreAuthorize("hasAuthority('MISSION_READ')")
    public List<MissionResponse> getAllMission() {
        List<Mission> missions = missionRepository.findAll();
        return missions.stream().map(this::maptoMissionResponse).toList();
    }
}

/*

hasAuthority('MISSION_CREATE') - This annotation is used to specify that the method can only be
accessed by users who have the MISSION_CREATE authority. If a user does not have this authority,
they will receive a 403 Forbidden response when trying to access the method.

hasRole('ADMIN') - This annotation is used to specify that the method can only be accessed by users
who have the ADMIN role. If a user does not have this role, they will receive a 403 Forbidden
response when trying to access the method.

*/
