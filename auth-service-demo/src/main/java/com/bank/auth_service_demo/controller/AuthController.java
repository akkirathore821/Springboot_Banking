package com.bank.auth_service_demo.controller;

import com.bank.auth_service_demo.model.User;
import com.bank.auth_service_demo.model.UserRequest;
import com.bank.auth_service_demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository repository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/create_user")
    public ResponseEntity<String> createUser(@RequestBody UserRequest request){

        User newUser = User.builder()
                .username(request.getUsername())
//                .password(passwordEncoder.encode(request.getPassword()))
                .password(request.getPassword())
                .build();

        newUser = repository.save(newUser);

        return ResponseEntity.ok(newUser.getUsername());
    }

}
