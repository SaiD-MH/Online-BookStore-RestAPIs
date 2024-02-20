package com.springboot.bookstore.service.Impl;

import com.springboot.bookstore.entity.Role;
import com.springboot.bookstore.entity.User;
import com.springboot.bookstore.exception.BookStoreException;
import com.springboot.bookstore.payload.LoginDto;
import com.springboot.bookstore.payload.SignUpDto;
import com.springboot.bookstore.repository.RoleRepository;
import com.springboot.bookstore.repository.UserRepository;
import com.springboot.bookstore.security.JwtTokenProvider;
import com.springboot.bookstore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail()
                        , loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(SignUpDto signUpDto) {


        if (userRepository.existsByEmail(signUpDto.getEmail()))
            throw new BookStoreException(HttpStatus.BAD_REQUEST ,
                    "This email already exist try to login. ");


        Role role = roleRepository.findByName("ROLE_ADMIN").get();

        User user = new User();

        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);

        return "User Created Successfully";
    }
}

