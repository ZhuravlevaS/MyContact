package com.example.demo.MyContact.controller;

import com.example.demo.MyContact.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String email, @RequestParam String password, @RequestParam String name) {
        try {
            authService.registerUser(email, password, name);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (SQLException e) {
            return new ResponseEntity<>("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login") // 200 OK: 001dXNlckBleGFtcGxlLmNvbXxwYXNzd29yZDEyMw==
    public ResponseEntity<String> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            String token = authService.authenticate(email, password);
            if (token != null) {
                return new ResponseEntity<>(token, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
            }
        } catch (SQLException e) {
            return new ResponseEntity<>("Login failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
