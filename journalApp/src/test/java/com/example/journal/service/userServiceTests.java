package com.example.journal.service;

import com.example.journal.entity.User;
import com.example.journal.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class userServiceTests {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserService userService;

//    Before each tests
    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

//    After each test case
    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }


    @ParameterizedTest
//    @ValueSource(strings = {
//            "john_doe",
//            "jane_smith",
//            "alice_wonder"
//    })
    @ArgumentsSource(UserArguementsProvider.class)
    public void testFindByUserName(User user)
    {
        assertTrue(userService.saveNewEntryWithSecurity(user));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3",
            "2, 3, 5",
            "3, 4, 7"
    })
    public  void Test(int a,int b, int expected){
        assertEquals(expected, a+b);
    }
}
