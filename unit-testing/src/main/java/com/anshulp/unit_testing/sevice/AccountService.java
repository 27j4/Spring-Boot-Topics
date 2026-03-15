package com.anshulp.unit_testing.sevice;

import com.anshulp.unit_testing.entity.Account;
import com.anshulp.unit_testing.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(Account account) {
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        return accountRepository.save(account);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account updateUsername(Long id, String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        Account existingAccount = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));

        if (existingAccount.getUsername().equals(username)) {
            throw new IllegalArgumentException("New username must be different from the current username");
        }

        if (accountRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        Account account = getAccountById(id);
        account.setUsername(username);
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found");
        }
        accountRepository.deleteById(id);
    }
}
