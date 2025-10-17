package com.bank.auth_service_demo.controller;

import com.bank.auth_service_demo.model.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @PostMapping("/getUser")
    public ResponseEntity<String> getUser(@RequestBody UserRequest request){
        return ResponseEntity.ok("User Controller");
    }

}
