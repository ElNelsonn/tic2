package com.cascardo.backend.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DateTimeConflictException extends RuntimeException {

    public DateTimeConflictException(String message) {
        super(message);
    }

}
