package com.cascardo.backend.controllers;


import com.cascardo.backend.dto.AddDataDto;
import com.cascardo.backend.dto.CreateUserDto;
import com.cascardo.backend.dto.SuccessResponse;
import com.cascardo.backend.services.DataService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @PostMapping("")
    public ResponseEntity<SuccessResponse> addUser(@Valid @RequestBody AddDataDto addDataDto,
                                                   HttpServletRequest request) {

        dataService.saveData(addDataDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Data agregada con exito.")
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .build()
                );
    }



}
