package com.yourplatform.leetcodeclone.controller;

import com.yourplatform.leetcodeclone.dto.UserDto;
import com.yourplatform.leetcodeclone.service.UserService; // Updated import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService; // Use service

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers(); // Call service
    }
}