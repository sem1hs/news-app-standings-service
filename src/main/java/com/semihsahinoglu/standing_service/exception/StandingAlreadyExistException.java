package com.semihsahinoglu.standing_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class StandingAlreadyExistException extends RuntimeException {
    public StandingAlreadyExistException(String message) {
        super(message);
    }
}
