package com.bank.api_gateway.service;


import com.bank.api_gateway.feign.FeignAuthenticationClient;
import com.bank.api_gateway.model.AuthDetailsRequest;
import com.bank.api_gateway.model.AuthDetailsResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private FeignAuthenticationClient feignAuthenticationClient;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        log.info("UserDetailsServiceImpl : Init");
//        AuthDetailsResponse authDetails = feignAuthenticationClient.getAuthDetails(new AuthDetailsRequest(username));
//        log.info("UserDetailsServiceImpl : " + authDetails.toString());
//        if (authDetails == null) {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(authDetails.getUsername())
//                .password(authDetails.getPassword())
////                    .roles(user.getRoles().toArray(new String[0]))
//                .build();
//    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        log.info("Loading user details for username: {}", username);

//        feignAuthenticationClient.getAuthDetails(new AuthDetailsRequest(username))
//                .doOnNext(resp -> log.info("Auth Details: " + resp));

        return feignAuthenticationClient.getAuthDetails(new AuthDetailsRequest(username))
                .map(authDetails -> User
                        .withUsername(authDetails.getUsername())
                        .password(authDetails.getPassword())
                        .build()
                )
                .switchIfEmpty(Mono.error(new RuntimeException("User not found: " + username)));
    }

}
