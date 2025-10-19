package com.bank.auth_service.service;

import com.bank.auth_service.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    public static final String SECRET = "536CPO736VIDOEX9823OABV7639DFRX7H92F42DJC09SMCSPSMSP62516554SCHOSD685437";

    public String generateToken(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Map<String, Object> claims = new HashMap<>();
        return jwtUtils.createToken(claims, userDetails);
    }
}
