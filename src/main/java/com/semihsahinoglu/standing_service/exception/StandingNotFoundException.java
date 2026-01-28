package com.semihsahinoglu.standing_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StandingNotFoundException extends RuntimeException {
    public StandingNotFoundException(String message) {
        super(message);
    }
}
