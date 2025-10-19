package com.bank.auth_service.service;

import com.bank.auth_service.exception.WrongCredentialsException;
import com.bank.auth_service.model.AuthDetails;
import com.bank.auth_service.model.AuthRequest;
import com.bank.auth_service.model.JWTTokenResponse;
import com.bank.auth_service.model.RegisterResponse;
import com.bank.auth_service.repo.AuthRepository;
import lombok.extern.slf4j.Slf4j;
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
    private AuthRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public JWTTokenResponse login(AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        if(authentication.isAuthenticated()){
            return JWTTokenResponse.builder()
                    .token(jwtService.generateToken(request.getUsername()))
                    .build();
        }else throw new WrongCredentialsException("Wrong credentials");
    }

    public RegisterResponse register(AuthRequest request) {
        AuthDetails newUser = AuthDetails.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        newUser = repository.save(newUser);

        return RegisterResponse.builder()
                .username(newUser.getUsername())
                .build();
    }
}
