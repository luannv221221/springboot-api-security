package com.example.securityjwt.controller;

import com.example.securityjwt.dto.request.UserRequestDTO;
import com.example.securityjwt.dto.response.UserResponseDTO;
import com.example.securityjwt.model.User;
import com.example.securityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO userResponseDTO = userService.login(userRequestDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        return new ResponseEntity<>(userService.register(user),HttpStatus.OK);
    }
}
