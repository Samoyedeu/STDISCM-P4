package com.example.auth.controller;

import com.example.auth.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    public static class LoginRequest {
        public String username;
        public String password;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
        System.out.println("Login POST called");
        System.out.println("Username: " + login.username);
        System.out.println("Password: " + login.password);

        if ("student1".equals(login.username) && "pass123".equals(login.password)) {
            String token = jwtUtil.generateToken(login.username, "student");
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
