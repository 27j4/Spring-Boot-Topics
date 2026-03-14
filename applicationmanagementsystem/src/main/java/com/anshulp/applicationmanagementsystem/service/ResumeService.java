package com.anshulp.applicationmanagementsystem.service;

import com.anshulp.applicationmanagementsystem.entity.Applicant;
import com.anshulp.applicationmanagementsystem.entity.Resume;
import com.anshulp.applicationmanagementsystem.repository.ApplicantJpaRepository;
import com.anshulp.applicationmanagementsystem.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final ApplicantJpaRepository applicantJpaRepository;

    public Resume createResume(Long applicantId, Resume resume) {
        Applicant applicant = applicantJpaRepository.findById(applicantId)
                .orElseThrow(() -> new RuntimeException("Applicant not found with id: " + applicantId));
        resume.setApplicant(applicant);
        return resumeRepository.save(resume);
    }
}
