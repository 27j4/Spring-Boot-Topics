package com.anshulp.applicationmanagementsystem.service;

import com.anshulp.applicationmanagementsystem.entity.Applicant;
import com.anshulp.applicationmanagementsystem.entity.Job;
import com.anshulp.applicationmanagementsystem.repository.ApplicantJpaRepository;
import com.anshulp.applicationmanagementsystem.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final ApplicantJpaRepository applicantJpaRepository;

    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public void addJobToApplicant(Long jobId, Long applicantId) {
        Job job = getJobById(jobId);
        Applicant applicant = applicantJpaRepository.findById(applicantId).orElseThrow(() -> new RuntimeException("Applicant not found"));
        job.getApplicants().add(applicant);
        applicant.getAppliedJobs().add(job);
        jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}
