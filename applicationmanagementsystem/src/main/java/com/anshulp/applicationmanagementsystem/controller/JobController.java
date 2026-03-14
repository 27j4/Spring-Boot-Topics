package com.anshulp.applicationmanagementsystem.controller;

import com.anshulp.applicationmanagementsystem.entity.Job;
import com.anshulp.applicationmanagementsystem.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return jobService.createJob(job);
    }

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @PostMapping("/{jobId}/applicants/{applicantId}")
    public void addJobToApplicant(@PathVariable Long jobId, @PathVariable Long applicantId) {
        jobService.addJobToApplicant(jobId, applicantId);
    }

}
