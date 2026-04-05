package com.anshulp.transaction.service;

import com.anshulp.transaction.repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class UserServiceTest {

    @Mock
    protected UserRepository userRepository;

    @InjectMocks
    protected UserService userService;


}