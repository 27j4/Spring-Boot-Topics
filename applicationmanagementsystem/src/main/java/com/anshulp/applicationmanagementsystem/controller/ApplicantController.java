package com.anshulp.applicationmanagementsystem.controller;

import com.anshulp.applicationmanagementsystem.dto.ApplicantRequestDto;
import com.anshulp.applicationmanagementsystem.dto.ApplicantResponseDto;
import com.anshulp.applicationmanagementsystem.entity.Applicant;
import com.anshulp.applicationmanagementsystem.entity.Application;
import com.anshulp.applicationmanagementsystem.entity.Resume;
import com.anshulp.applicationmanagementsystem.service.ApplicantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applicants")
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @GetMapping
    public List<Applicant> getAllApplicants() {
        return applicantService.getAllApplicants();
    }

    @GetMapping("/{id}")
    public ApplicantResponseDto getApplicantById(@PathVariable Long id) {
        Applicant applicant = applicantService.getApplicantById(id);
        return new ApplicantResponseDto(applicant.getId(), applicant.getName(), applicant.getEmail(), applicant.getPhone(), applicant.getStatus());
    }

    @GetMapping("/getApplications/cache")
    public List<Application> method2(){
        return applicantService.method2();
    }

    @PostMapping
    public ApplicantResponseDto createApplicant(@Valid @RequestBody ApplicantRequestDto applicantRequestDto) {
        return applicantService.createApplicant(applicantRequestDto);
    }

    @GetMapping("/page")
    public Page<ApplicantResponseDto> getApplicantsWithPagination(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return applicantService.getApplicantsWithPagination(page, size);
    }

    @GetMapping("/partial")
    public Page<ApplicantResponseDto> getApplicantsByPartialName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String name) {
        return applicantService.getApplicantsByPartialName(page, size, name);
    }

    @GetMapping("/{applicantId}/resume")
    public Resume getResumeByApplicantId(@PathVariable Long applicantId) {
        return applicantService.getResumeByApplicantId(applicantId);
    }

    @PostMapping("/{applicantId}/update-status")
    public ApplicantResponseDto updateApplicantStatus(@PathVariable Long applicantId, @RequestParam String status) {
        return applicantService.updateApplicantStatus(applicantId, status);
    }

}


