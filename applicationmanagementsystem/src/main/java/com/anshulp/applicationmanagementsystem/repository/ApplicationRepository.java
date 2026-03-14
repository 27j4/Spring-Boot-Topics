package com.anshulp.applicationmanagementsystem.repository;


import com.anshulp.applicationmanagementsystem.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
