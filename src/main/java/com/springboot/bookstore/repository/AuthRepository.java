package com.springboot.bookstore.repository;

import com.springboot.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User, Long> {

}
