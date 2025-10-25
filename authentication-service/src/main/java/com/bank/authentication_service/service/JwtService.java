package com.bank.authentication_service.service;

import com.bank.authentication_service.jwt.JwtUtils;
import com.bank.authentication_service.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {

    private final UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    public JwtService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(String username, String accountNumber, Roles roles) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Map<String, Object> claims = new HashMap<>();
        claims.put("accountNumber",accountNumber);
        claims.put("role", roles);
        return jwtUtils.createToken(claims, userDetails);
    }
}
