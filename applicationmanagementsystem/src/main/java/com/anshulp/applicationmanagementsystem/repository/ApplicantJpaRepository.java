package com.anshulp.applicationmanagementsystem.repository;

import com.anshulp.applicationmanagementsystem.entity.Applicant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicantJpaRepository extends JpaRepository<Applicant, Long> {
    @Query("SELECT a FROM Applicant a WHERE a.name LIKE %:name%")
    Page<Applicant> findByPartial(Pageable pageable, String name);
}
