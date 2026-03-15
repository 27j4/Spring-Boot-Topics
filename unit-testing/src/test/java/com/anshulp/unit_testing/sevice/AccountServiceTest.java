package com.anshulp.unit_testing.sevice;

import com.anshulp.unit_testing.entity.Account;
import com.anshulp.unit_testing.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/*
1. Create mock dependencies
2. Inject mocks into class
3. Define mock behaviour (when...)
4. Call the method under test
5. Assert the result
*/


@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void testCreateAccount() {

        // Given
        String username = "ansh_iet_22";
        String email = "pandeyanshul2004@gmail.com";

        Account account = new Account();
        account.setUsername(username);
        account.setEmail(email);

        // When
        when(accountRepository.existsByUsername(username)).thenReturn(false);
        when(accountRepository.save(account)).thenReturn(account);

        Account createdAccount = accountService.createAccount(account);

        // Then

        assertNotNull(createdAccount);
        assertEquals(username, createdAccount.getUsername()); // expected vs actual
        assertEquals(email, createdAccount.getEmail());
    }

    @Test
    public void testGetAccountById() {
        // Given
        Long accountId = 1L;
        String username = "ansh_iet_22";
        String email = "pandeyanshul2004@gmail.com";

        Account account = new Account();
        account.setId(accountId);
        account.setUsername(username);
        account.setEmail(email);

        // When
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        Account retrievedAccount = accountService.getAccountById(accountId);

        // Then
        assertNotNull(retrievedAccount);
        assertEquals(accountId, retrievedAccount.getId());
        assertEquals(username, retrievedAccount.getUsername());
        assertEquals(email, retrievedAccount.getEmail());
    }

    @Test
    public void testDeleteAccount() {
        // Given
        Long accountId = 1L;

        // When
        when(accountRepository.existsById(accountId)).thenReturn(true);

        // Then
        // No exception should be thrown, and the deleteById method should be called
        assertDoesNotThrow(() -> accountService.deleteAccount(1L), "Expected deleteAccount to not throw an exception");
    }

    @Test
    public void testDeleteAccount_NotFound() {
        // Given
        Long accountId = 1L;

        // When
        when(accountRepository.existsById(accountId)).thenReturn(false);

        // Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> accountService.deleteAccount(accountId));
        assertEquals("Account not found", exception.getMessage());
    }

    @Test
    public void testUpdateUsername() {
        // Given
        Long accountId = 1L;
        String oldUsername = "ansh_iet_22";
        String newUsername = "ansh_iet";
        String email = "pandeyanshul2004@gmail.com";

        Account account = new Account();
        account.setId(accountId);
        account.setUsername(oldUsername);
        account.setEmail(email);

        // when
        when(accountRepository.existsByUsername(newUsername)).thenReturn(false);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        // Then
        // No exception should be thrown, and the save method should be called with the updated account

        Account updatedAccount = accountService.updateUsername(accountId, newUsername);
        assertNotNull(updatedAccount);
        assertEquals(newUsername, updatedAccount.getUsername());
        assertEquals(email, updatedAccount.getEmail());
    }

}