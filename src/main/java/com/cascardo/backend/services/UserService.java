package com.cascardo.backend.services;


import com.cascardo.backend.dto.CreateUserDto;
import com.cascardo.backend.dto.UserDto;
import com.cascardo.backend.exceptions.EmailAlreadyInUseException;
import com.cascardo.backend.exceptions.UserNotFoundException;
import com.cascardo.backend.mappers.UserMapper;
import com.cascardo.backend.models.User;
import com.cascardo.backend.repositories.UserRepository;
import com.cascardo.backend.validators.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void createUser(CreateUserDto createUserDto) throws EmailAlreadyInUseException {

        userValidator.validateEmailIsNotInUse(createUserDto.email());

        String hashedPassword = passwordEncoder.encode(createUserDto.password());
        User newUser = UserMapper.toEntity(createUserDto, hashedPassword);

        userRepository.save(newUser);
    }


    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .map(u -> UserMapper.toDto(u) )
                .orElseThrow(() -> new UserNotFoundException("El usuario no existe."));

    }










}
