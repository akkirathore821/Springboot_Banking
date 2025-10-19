package com.bank.user_service.controller;

import com.bank.user_service.model.User;
import com.bank.user_service.model.UserDto;
import com.bank.user_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//  Todo {allow only for Role-Admin}
    @PostMapping("/create_user")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }



//    Todo later use
//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//    }
}
