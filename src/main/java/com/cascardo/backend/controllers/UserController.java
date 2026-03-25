package com.cascardo.backend.controllers;


import com.cascardo.backend.dto.CreateUserDto;
import com.cascardo.backend.dto.SuccessResponse;
import com.cascardo.backend.dto.UserDto;
import com.cascardo.backend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @PostMapping("")
    public ResponseEntity<SuccessResponse> addUser(@Valid @RequestBody CreateUserDto createUserDto,
                                                   HttpServletRequest request) {

        userService.createUser(createUserDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Usuario creado con exito.")
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .build()
                );
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email,
                                           HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.getUserByEmail(email));
    }




}
