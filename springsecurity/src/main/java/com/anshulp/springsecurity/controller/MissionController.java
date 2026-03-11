package com.anshulp.springsecurity.controller;

import com.anshulp.springsecurity.dto.MissionRequest;
import com.anshulp.springsecurity.dto.MissionResponse;
import com.anshulp.springsecurity.entity.User;
import com.anshulp.springsecurity.security.CustomUserDetails;
import com.anshulp.springsecurity.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController {

    private final MissionService missionService;

    @PostMapping("/create")
    public ResponseEntity<MissionResponse> createOrder(@RequestBody MissionRequest missionRequest,
                                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        MissionResponse missionResponse = missionService.createOrder(missionRequest, user);
        return ResponseEntity.ok(missionResponse);
    }

    @DeleteMapping("/delete/{missionId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long missionId,
                                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        String response = missionService.deleteOrder(missionId, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MissionResponse>> getOrders() {
        return ResponseEntity.ok(missionService.getAllMission());
    }
}
