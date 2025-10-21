package com.bank.authentication_service.service;

import com.bank.authentication_service.exception.WrongCredentialsException;
import com.bank.authentication_service.feign.FeignAccountClient;
import com.bank.authentication_service.model.*;
import com.bank.authentication_service.repo.AuthRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

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
    @Autowired
    private FeignAccountClient feignAccountClient;


    public JWTTokenResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        if(authentication.isAuthenticated()){
            AuthDetails authDetails = repository.findByUsername(request.getUsername());
            return JWTTokenResponse.builder()
                    .token(jwtService.generateToken(authDetails.getUsername(),authDetails.getAccountNumber()))
                    .build();
        }else throw new WrongCredentialsException("Wrong credentials");
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        AuthDetails newUser = AuthDetails.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountNumber(generateAccountNumber())
                .build();
        newUser = repository.save(newUser);

///        Calling the create Api of account-service (holder & number)
        AccountResponse accountResponse = feignAccountClient.createAccount(
                 new CreateAccountRequest(newUser.getAccountNumber(),request.getAccountHolder()));

        return RegisterResponse.builder()
                .username(newUser.getUsername())
                .accountHolder(accountResponse.getAccountHolder())
                .accountNumber(accountResponse.getAccountNumber())
                .build();
    }


    //Todo Improvise this function
    private String generateAccountNumber() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return "ACC" + number;
    }
}
