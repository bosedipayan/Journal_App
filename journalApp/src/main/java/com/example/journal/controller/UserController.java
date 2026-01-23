package com.example.journal.controller;

import com.example.journal.entity.User;
import com.example.journal.repository.UserRepository;
import com.example.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    private UserRepository userRepository;

//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @PostMapping
//    public void addNewUser(@RequestBody User user){
//        userService.saveNewEntryWithSecurity(user);
//    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        User userInDB = userService.findByUsername(currentUsername).orElse(null);
        if(userInDB != null){
            userInDB.setUsername(user.getUsername());
            userInDB.setPassword((user.getPassword()));
            userService.saveEntry(userInDB);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        userRepository.deleteEntryById(currentUsername);

        return new ResponseEntity<>(HttpStatus.GONE);
    }
}
