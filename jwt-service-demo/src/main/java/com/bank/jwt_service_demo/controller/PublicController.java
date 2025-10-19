package com.bank.jwt_service_demo.controller;

import com.bank.jwt_service_demo.model.JWTTokenResponse;
import com.bank.jwt_service_demo.model.LoginRequest;
import com.bank.jwt_service_demo.model.RegisterRequest;
import com.bank.jwt_service_demo.model.RegisterResponse;
import com.bank.jwt_service_demo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JWTTokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
