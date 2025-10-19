package com.bank.jwt_service_demo.service;

import com.bank.jwt_service_demo.jwt.JwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.Date;
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
