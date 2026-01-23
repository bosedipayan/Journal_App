package com.example.journal.controller;

import com.example.journal.entity.User;
import com.example.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health")
    public  String health() {
        return "OK";
    }

    @PostMapping("/create-user")
    public void addNewUser(@RequestBody User user){
        userService.saveNewEntryWithSecurity(user);
    }

}
