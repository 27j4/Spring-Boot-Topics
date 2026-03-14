package com.anshulp.applicationmanagementsystem.controller;

import com.anshulp.applicationmanagementsystem.entity.Resume;
import com.anshulp.applicationmanagementsystem.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/resumes")
public class ResumeController {
    private final ResumeService resumeService;

    @PostMapping("/{applicantId}")
    public Resume createResume(@PathVariable Long applicantId, @RequestBody Resume resume) {
        return resumeService.createResume(applicantId, resume);
    }
}
