package com.springboot.bookstore.repository;

import com.springboot.bookstore.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {


    boolean existsByBookIdAndUserId(Long bookId , Long userId );

    Optional<Cart> findByBookIdAndUserId(long bookId , long userId);

    List<Cart> findAllByUserId(long userId);
}
