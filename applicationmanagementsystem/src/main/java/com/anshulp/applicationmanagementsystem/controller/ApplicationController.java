package com.anshulp.applicationmanagementsystem.controller;

import com.anshulp.applicationmanagementsystem.entity.Application;
import com.anshulp.applicationmanagementsystem.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/{applicantId}")
    public ResponseEntity<Application> createApplication(@PathVariable Long applicantId, @RequestBody Application application) {
        return ResponseEntity.ok(applicationService.createApplication(application, applicantId));
    }
}
