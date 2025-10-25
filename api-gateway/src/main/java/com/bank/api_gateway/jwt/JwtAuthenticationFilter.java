package com.bank.api_gateway.jwt;

import com.bank.api_gateway.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    @Autowired
    private JwtUtils jwtUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

//        log.info("Exchange : " + exchange.getRequest().);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        return userDetailsService.findByUsername(username)
                .flatMap(userDetails -> {
                    if (jwtUtil.isTokenValid(token, userDetails)) {

                        String accountNumber = jwtUtil.extractAccountNumber(token);
                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header("accountNumber", accountNumber)
                                .build();

                        String role = jwtUtil.extractRoles(token);
                        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

                        log.info("JwtAuthenticationFilter : authorities : " + authorities.toString());

                        Authentication auth = new UsernamePasswordAuthenticationToken(
                                userDetails, null,authorities);

                        log.info("JwtAuthenticationFilter : authorities : " + authorities.toString());

                        return chain.filter(exchange.mutate().request(mutatedRequest).build())
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
                    }
                    return chain.filter(exchange);
                });
    }
}
