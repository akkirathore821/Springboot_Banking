package com.bank.auth_service.service;

import com.bank.auth_service.exceptions.WrongCredentialException;
import com.bank.auth_service.model.JWTTokenResponse;
import com.bank.auth_service.model.LoginRequest;
import com.bank.auth_service.model.RegisterRequest;
import com.bank.auth_service.model.RegisterResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.WriteAbortedException;
import java.lang.WrongThreadException;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserServiceClient userServiceClient;
    private final JwtService jwtService;

    public JWTTokenResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            return JWTTokenResponse.builder()
                    .token(jwtService.generateToken(loginRequest.getUsername()))
                    .build();
        }else throw new WrongCredentialException("Wrong Credential");
    }


    //Todo
//    public RegisterResponse register(RegisterRequest registerRequest) {
//    }
}
