package com.bank.jwt_service_demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class WrongCredentialsException extends RuntimeException{
    public WrongCredentialsException(String exMsg){
        super(exMsg);
    }
}
