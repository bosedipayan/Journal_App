package com.example.journal.service;

import com.example.journal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class userServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUserName()
    {
        assertNotNull(userRepository.findByUsername("Jisu"));
        assertEquals(4, 2+2);
    }
}
