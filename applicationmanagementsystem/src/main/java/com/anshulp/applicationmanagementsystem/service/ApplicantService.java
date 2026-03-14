package com.anshulp.applicationmanagementsystem.service;

import com.anshulp.applicationmanagementsystem.dto.ApplicantRequestDto;
import com.anshulp.applicationmanagementsystem.dto.ApplicantResponseDto;
import com.anshulp.applicationmanagementsystem.entity.Applicant;
import com.anshulp.applicationmanagementsystem.entity.Application;
import com.anshulp.applicationmanagementsystem.entity.Resume;
import com.anshulp.applicationmanagementsystem.repository.ApplicantJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantJpaRepository applicantJpaRepository;
    Applicant tapplicant;

    public List<Applicant> getAllApplicants() {
        List<Applicant> applicants = applicantJpaRepository.findAll();
        return applicants;
//        return applicants.stream()
//                .map(applicant -> new ApplicantResponseDto(applicant.getId(), applicant.getName(), applicant.getEmail(), applicant.getPhone(), applicant.getStatus()))
//                .collect(Collectors.toList());
    }


    //    @Transactional
    public Applicant getApplicantById(Long id) {
        return tapplicant = applicantJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Applicant not found with id: " + id));
    }

    public List<Application> method2() {
        tapplicant = applicantJpaRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Applicant not found with id: " + 1L));
        return tapplicant.getApplications();
    }

    public ApplicantResponseDto createApplicant(ApplicantRequestDto requestDto) {
        Applicant applicant = new Applicant();
        applicant.setName(requestDto.getName());
        applicant.setEmail(requestDto.getEmail());
        applicant.setPhone(requestDto.getPhone());

        Applicant savedApplicant = applicantJpaRepository.save(applicant);
        return new ApplicantResponseDto(savedApplicant.getId(), savedApplicant.getName(), savedApplicant.getEmail(), savedApplicant.getPhone(), savedApplicant.getStatus());
    }

    @Transactional
    public ApplicantResponseDto updateApplicantStatus(Long id, String status) {
        Applicant applicant = applicantJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Applicant not found with id: " + id));
        applicant.setStatus(status);
        return new ApplicantResponseDto(applicant.getId(), applicant.getName(), applicant.getEmail(), applicant.getPhone(), applicant.getStatus());
    }

    public Page<ApplicantResponseDto> getApplicantsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return applicantJpaRepository.findAll(pageable)
                .map(applicant -> new ApplicantResponseDto(applicant.getId(), applicant.getName(), applicant.getEmail(), applicant.getPhone(), applicant.getStatus()));
    }

    public Page<ApplicantResponseDto> getApplicantsByPartialName(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        return applicantJpaRepository.findByPartial(pageable, name)
                .map(applicant -> new ApplicantResponseDto(applicant.getId(), applicant.getName(), applicant.getEmail(), applicant.getPhone(), applicant.getStatus()));
    }

    public Resume getResumeByApplicantId(Long applicantId) {
        Applicant applicant = applicantJpaRepository.findById(applicantId)
                .orElseThrow(() -> new RuntimeException("Applicant not found with id: " + applicantId));
        return applicant.getResume();
    }
}
