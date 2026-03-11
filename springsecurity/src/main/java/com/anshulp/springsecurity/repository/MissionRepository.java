package com.anshulp.springsecurity.repository;

import com.anshulp.springsecurity.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByUserId(Long id);
}
