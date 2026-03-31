package com.cascardo.backend.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class FileException extends RuntimeException {
    public FileException(String message) {
        super(message);
    }
}
