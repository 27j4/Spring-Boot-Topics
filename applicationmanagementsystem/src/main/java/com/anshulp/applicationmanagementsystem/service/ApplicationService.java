package com.anshulp.applicationmanagementsystem.service;

import com.anshulp.applicationmanagementsystem.entity.Applicant;
import com.anshulp.applicationmanagementsystem.entity.Application;
import com.anshulp.applicationmanagementsystem.repository.ApplicantJpaRepository;
import com.anshulp.applicationmanagementsystem.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicantJpaRepository applicantJpaRepository;

    public Application createApplication(Application application, Long applicantId) {
        Applicant applicant = applicantJpaRepository.findById(applicantId).orElseThrow(
                () -> new RuntimeException("Applicant not found with id: " + applicantId)
        );
        application.setApplicant(applicant);
        return applicationRepository.save(application);
    }

}
