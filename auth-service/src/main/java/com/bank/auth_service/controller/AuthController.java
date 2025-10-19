package com.bank.auth_service.controller;

import com.bank.auth_service.model.AuthRequest;
import com.bank.auth_service.model.JWTTokenResponse;
import com.bank.auth_service.model.RegisterResponse;
import com.bank.auth_service.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JWTTokenResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
