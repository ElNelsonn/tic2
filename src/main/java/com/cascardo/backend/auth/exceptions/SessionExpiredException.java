package com.cascardo.backend.auth.exceptions;

import com.nightmap.backend.shared.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SessionExpiredException extends BusinessException {

    public SessionExpiredException(String message) {
        super(message);
    }
}
