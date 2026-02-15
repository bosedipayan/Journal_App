package com.example.journal.service;


import com.example.journal.entity.User;
import com.example.journal.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

// For Mockito

@SpringBootTest
public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    // Instance gets created automatically and the mocked dependencies are injected into it.

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUserNameTest() {
        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(User.builder().username("bose").password("bose").roles(new ArrayList<>()) .build());
        UserDetails user = userDetailsService.loadUserByUsername("bose");
        Assertions.assertNotNull(user);
    }
}
