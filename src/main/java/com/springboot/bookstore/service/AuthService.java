package com.springboot.bookstore.service;

import com.springboot.bookstore.payload.LoginDto;
import com.springboot.bookstore.payload.SignUpDto;

public interface AuthService {



    String login(LoginDto loginDto);
    String register(SignUpDto signUpDto);

}
