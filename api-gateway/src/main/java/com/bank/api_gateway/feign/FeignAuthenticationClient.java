package com.bank.api_gateway.feign;


import com.bank.api_gateway.model.AuthDetailsRequest;
import com.bank.api_gateway.model.AuthDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "authentication-service", path = "/api/auth/auth_details")
public interface FeignAuthenticationClient {

    @PostMapping(value = "/getAuthDetails",consumes = "application/json")
    Mono<AuthDetailsResponse> getAuthDetails(@RequestBody AuthDetailsRequest request);

}