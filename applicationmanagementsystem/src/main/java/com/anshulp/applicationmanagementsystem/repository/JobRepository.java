package com.anshulp.applicationmanagementsystem.repository;

import com.anshulp.applicationmanagementsystem.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

}
