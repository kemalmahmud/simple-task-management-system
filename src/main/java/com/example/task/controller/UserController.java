package com.example.task.controller;

import com.example.task.dto.request.LoginRequest;
import com.example.task.dto.request.RegisterUserRequest;
import com.example.task.dto.response.BaseResponse;
import com.example.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> registerUser(@RequestBody RegisterUserRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
