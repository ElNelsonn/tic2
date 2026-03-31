package com.cascardo.backend.controllers;

import com.cascardo.backend.dto.CreateUserDto;
import com.cascardo.backend.dto.SuccessResponse;
import com.cascardo.backend.dto.UserDto;
import com.cascardo.backend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Test
    void addUser_returnsCreated() {
        UserService userService = mock(UserService.class);
        UserController controller = new UserController(userService);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getRequestURI()).thenReturn("/api/users");

        var dto = new CreateUserDto("Juan", "Perez", "juan@mail.com", "Abc12345!");

        var response = controller.addUser(dto, req);

        assertEquals(201, response.getStatusCode().value());
        SuccessResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("/api/users", body.path());
        verify(userService).createUser(dto);
    }

    @Test
    void getUserByEmail_returnsBody() {
        UserService userService = mock(UserService.class);
        UserController controller = new UserController(userService);
        HttpServletRequest req = mock(HttpServletRequest.class);

        UserDto userDto = UserDto.builder().name("Juan").lastName("Perez").email("juan@mail.com").build();
        when(userService.getUserByEmail("juan@mail.com")).thenReturn(userDto);

        var response = controller.getUserByEmail("juan@mail.com", req);

        assertEquals(201, response.getStatusCode().value()); // estado actual del controlador
        assertEquals("juan@mail.com", response.getBody().email());
    }
}