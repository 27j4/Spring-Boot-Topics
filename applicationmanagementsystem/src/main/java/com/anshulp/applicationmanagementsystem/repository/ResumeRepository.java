package com.anshulp.applicationmanagementsystem.repository;

import com.anshulp.applicationmanagementsystem.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
