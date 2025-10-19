package com.bank.jwt_service_demo.service;

import com.bank.jwt_service_demo.exception.WrongCredentialsException;
import com.bank.jwt_service_demo.model.*;
import com.bank.jwt_service_demo.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public JWTTokenResponse login(LoginRequest request) {

        log.info("AuthService : JWTTokenResponse : Init");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        if(authentication.isAuthenticated()){
            log.info("AuthService : JWTTokenResponse : Authenticated");
            return JWTTokenResponse.builder()
                    .token(jwtService.generateToken(request.getUsername()))
                    .build();
        }else throw new WrongCredentialsException("Wrong credentials");
    }

    public RegisterResponse register(RegisterRequest request) {
        User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();
        newUser = repository.save(newUser);

        return RegisterResponse.builder()
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .build();
    }
}
