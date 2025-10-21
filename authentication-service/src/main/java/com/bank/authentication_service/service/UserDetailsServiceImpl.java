package com.bank.authentication_service.service;

import com.bank.authentication_service.model.AuthDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.bank.authentication_service.repo.AuthRepository;

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthRepository authRepository;

    public UserDetailsServiceImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

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
