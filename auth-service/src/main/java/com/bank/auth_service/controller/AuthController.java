package com.bank.auth_service.controller;

import com.bank.auth_service.model.JWTTokenResponse;
import com.bank.auth_service.model.LoginRequest;
import com.bank.auth_service.model.RegisterRequest;
import com.bank.auth_service.model.RegisterResponse;
import com.bank.auth_service.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    public final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JWTTokenResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));
    }

}
