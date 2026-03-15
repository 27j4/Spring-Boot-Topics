package com.anshulp.unit_testing.repository;

import com.anshulp.unit_testing.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testExistsByUsername() {

        // Given
        String username = "testuser";
        String email = "pandeyanshul2004@gmail.com";

        Account account = new Account();
        account.setUsername(username);
        account.setEmail(email);

        entityManager.persist(account);
        entityManager.flush();

        // When
        boolean exists = accountRepository.existsByUsername(username);

        // Then
        assertTrue(exists);
    }
}