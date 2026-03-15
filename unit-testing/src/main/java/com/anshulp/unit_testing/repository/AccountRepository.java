package com.anshulp.unit_testing.repository;

import com.anshulp.unit_testing.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByUsername(String username);
}
