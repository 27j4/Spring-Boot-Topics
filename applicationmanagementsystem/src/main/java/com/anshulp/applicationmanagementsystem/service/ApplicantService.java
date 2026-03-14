package com.anshulp.applicationmanagementsystem.service;

import com.anshulp.applicationmanagementsystem.dto.ApplicantRequestDto;
import com.anshulp.applicationmanagementsystem.dto.ApplicantResponseDto;
import com.anshulp.applicationmanagementsystem.entity.Applicant;
import com.anshulp.applicationmanagementsystem.entity.Resume;
import com.anshulp.applicationmanagementsystem.repository.ApplicantJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantJpaRepository applicantJpaRepository;

    public List<ApplicantResponseDto> getAllApplicants() {
        List<Applicant> applicants = applicantJpaRepository.findAll();
        return applicants.stream()
                .map(applicant -> new ApplicantResponseDto(applicant.getId(), applicant.getName(), applicant.getEmail(), applicant.getPhone(), applicant.getStatus()))
                .collect(Collectors.toList());
    }

    public ApplicantResponseDto createApplicant(ApplicantRequestDto requestDto) {
        Applicant applicant = new Applicant();
        applicant.setName(requestDto.getName());
        applicant.setEmail(requestDto.getEmail());
        applicant.setPhone(requestDto.getPhone());

        Applicant savedApplicant = applicantJpaRepository.save(applicant);
        return new ApplicantResponseDto(savedApplicant.getId(), savedApplicant.getName(), savedApplicant.getEmail(), savedApplicant.getPhone(), savedApplicant.getStatus());
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
