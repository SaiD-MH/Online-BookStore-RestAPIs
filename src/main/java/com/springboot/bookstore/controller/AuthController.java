package com.springboot.bookstore.controller;

import com.springboot.bookstore.payload.JWTResponseDto;
import com.springboot.bookstore.payload.LoginDto;
import com.springboot.bookstore.payload.SignUpDto;
import com.springboot.bookstore.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping(value = {"/login", "/signin"})

    public ResponseEntity<JWTResponseDto> authenticateUser(@RequestBody LoginDto loginDto) {

        JWTResponseDto jwtResponseDto = new JWTResponseDto();


        String token = authService.login(loginDto);
        jwtResponseDto.setAccessToken(token);


        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping(value = {"/signup", "/register"})

    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {


        String register = authService.register(signUpDto);
        return new ResponseEntity<>(register, HttpStatus.CREATED);

    }




}
