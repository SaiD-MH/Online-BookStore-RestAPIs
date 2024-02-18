package com.springboot.bookstore.service;

import com.springboot.bookstore.payload.CartDto;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface CartService {

    public List<CartDto> addToCart(CartDto cartDto, HttpSession session);
    public List<CartDto> getAllCartsByUserId( long userId, HttpSession session);

    public CartDto updateCart(CartDto cartDto , long userId , HttpSession session);

    public String deleteCart(Long bookId ,Long userId , HttpSession session);

}
