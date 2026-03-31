package com.cascardo.backend.services;

import com.cascardo.backend.dto.CreateUserDto;
import com.cascardo.backend.dto.UserDto;
import com.cascardo.backend.exceptions.UserNotFoundException;
import com.cascardo.backend.models.User;
import com.cascardo.backend.repositories.UserRepository;
import com.cascardo.backend.validators.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private UserValidator userValidator;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private UserService userService;

    @Test
    void createUser_ok() {
        CreateUserDto dto = new CreateUserDto("Juan", "Perez", "juan@mail.com", "Abc12345!");
        when(passwordEncoder.encode("Abc12345!")).thenReturn("hashed");

        userService.createUser(dto);

        verify(userValidator).validateEmailIsNotInUse("juan@mail.com");
        verify(passwordEncoder).encode("Abc12345!");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void getUserByEmail_ok() {
        User user = User.builder().id(1L).name("Juan").lastName("Perez").email("juan@mail.com").hashedPassword("x").build();
        when(userRepository.findByEmail("juan@mail.com")).thenReturn(Optional.of(user));

        UserDto result = userService.getUserByEmail("juan@mail.com");

        assertEquals("Juan", result.name());
        assertEquals("Perez", result.lastName());
        assertEquals("juan@mail.com", result.email());
    }

    @Test
    void getUserByEmail_notFound() {
        when(userRepository.findByEmail("x@mail.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail("x@mail.com"));
    }
}