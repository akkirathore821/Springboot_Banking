package com.bank.auth_service.service;

import com.bank.auth_service.model.AuthDetails;
import com.bank.auth_service.repo.AuthRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthDetails authDetails = authRepository.findByUsername(username);
        if (authDetails == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(authDetails.getUsername())
                .password(authDetails.getPassword())
//                    .roles(user.getRoles().toArray(new String[0]))
                .build();
    }
}
