package com.springboot.bookstore.security;

import com.springboot.bookstore.entity.Role;
import com.springboot.bookstore.entity.User;
import com.springboot.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookStoreUserDetails implements UserDetailsService {

    private UserRepository userRepository;


    @Autowired
    public BookStoreUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User with this email don't exist")
        );

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : user.getRoles()) {

            authorities.add(new SimpleGrantedAuthority(role.getName()));

        }

        return new org.springframework.security.core.userdetails
                .User(user.getEmail(), user.getPassword(), authorities);
    }


}