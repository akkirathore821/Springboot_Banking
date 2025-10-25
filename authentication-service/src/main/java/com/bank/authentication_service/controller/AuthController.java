package com.bank.authentication_service.controller;

import com.bank.authentication_service.model.*;
import com.bank.authentication_service.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JWTTokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

//    @PostMapping("/register")
//    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
//        return ResponseEntity.ok(authService.register(request));
//    }

//    @PostMapping("/getAuthDetails")
//    public ResponseEntity<AuthDetailsResponse> getAuthDetails(@RequestBody AuthDetailsRequest request) {
//        return ResponseEntity.ok(authService.getAuthDetails(request));
//    }
}
