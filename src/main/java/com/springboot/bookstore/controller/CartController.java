package com.springboot.bookstore.controller;

import com.springboot.bookstore.payload.CartDto;
import com.springboot.bookstore.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart") // for Not Logged Users user [0] as query param
public class CartController {




    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<List<CartDto>> addToCart(@RequestBody CartDto cartDto, HttpSession session) {


        return new ResponseEntity<>(cartService.addToCart(cartDto, session), HttpStatus.CREATED);


    }


    @GetMapping
    public ResponseEntity<List<CartDto>> getCart(@RequestParam("userId") long userId, HttpSession session) {


        return ResponseEntity.ok(cartService.getAllCartsByUserId(userId, session));

    }


    @PutMapping
    public ResponseEntity<CartDto> updateCart(
            @RequestBody CartDto cartDto,
            @RequestParam(value = "userId", defaultValue = "0") long userId,
            HttpSession session
    ) {


        return ResponseEntity.ok(cartService.updateCart(cartDto, userId, session));


    }


    @DeleteMapping
    public ResponseEntity<String> deleteCart(
            @RequestParam(value = "userId", defaultValue = "0") long userId,
            @RequestParam(value = "bookId") long bookId,
            HttpSession session

    ) {

        return ResponseEntity.ok(cartService.deleteCart(bookId, userId, session));
    }


}
