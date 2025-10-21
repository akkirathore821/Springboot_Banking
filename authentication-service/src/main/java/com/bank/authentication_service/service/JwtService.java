package com.bank.authentication_service.service;

import com.bank.authentication_service.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
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

    public static final String SECRET = "536CPO736VIDOEX9823OABV7639DFRX7H92F42DJC09SMCSPSMSP62516554SCHOSD685437";

    public JwtService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(String username, String accountNumber) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Map<String, Object> claims = new HashMap<>();
        claims.put("accountNumber",accountNumber);
        return jwtUtils.createToken(claims, userDetails);
    }
}
