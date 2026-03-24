package com.cascardo.backend.validators;


import com.cascardo.backend.exceptions.EmailAlreadyInUseException;
import com.cascardo.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateEmailIsNotInUse(String email) throws EmailAlreadyInUseException {

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyInUseException("El email ya se encuentra en uso.");
        }
    }





}
