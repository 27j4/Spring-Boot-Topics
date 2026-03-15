package com.anshulp.unit_testing.controller;

import com.anshulp.unit_testing.entity.Account;
import com.anshulp.unit_testing.sevice.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @MockitoBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAccount() throws Exception {
        // Given
        Account account = new Account();
        account.setId(1L);
        String username = "ansh_iet_22";
        String email = "pandeyanshul2004@gmail.com";
        account.setUsername(username);
        account.setEmail(email);
        String requestBody = """
                {
                  "id": 1,
                  "username": "%s",
                  "email": "%s"
                }
                """.formatted(username, email);

        // when
        when(accountService.createAccount(any(Account.class))).thenReturn(account);

        // then
        mockMvc.perform(post("/accounts/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.email").value(email));


    }

}