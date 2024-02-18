package com.springboot.bookstore.service.Impl;

import com.springboot.bookstore.entity.Book;
import com.springboot.bookstore.entity.Cart;
import com.springboot.bookstore.entity.User;
import com.springboot.bookstore.exception.BookStoreException;
import com.springboot.bookstore.exception.ResourceNotFoundException;
import com.springboot.bookstore.payload.CartDto;
import com.springboot.bookstore.repository.BookRepository;
import com.springboot.bookstore.repository.CartRepository;
import com.springboot.bookstore.repository.UserRepository;
import com.springboot.bookstore.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {


    private CartRepository cartRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private BookRepository bookRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,
                           UserRepository userRepository, BookRepository bookRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    public List<CartDto> addToCart(CartDto cartDto, HttpSession session) {

        List<CartDto> reponseCartsList = new ArrayList<>();

        if (isLoggedIn()) { // User Login so deal with Database

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            List<CartDto> dtoCartsToSave = new ArrayList<>();

            // check request as loggedIn user
            dtoCartsToSave.add(cartDto);

            // Get Carts that he made while he is not logged

            Map<Long, CartDto> sessionData = (Map<Long, CartDto>) session.getAttribute("sessionData");

            if (sessionData != null) {

                sessionData.forEach(
                        (bookId, cart) -> dtoCartsToSave.add(cart)

                );

                sessionData.clear();

            }


            String email = authentication.getName();
            User user = userRepository.findByEmail(email).get();


            for (CartDto dtocart : dtoCartsToSave) {

                Book book = bookRepository.findById(dtocart.getBookId()).get();

                if (!cartRepository.existsByBookIdAndUserId(dtocart.getBookId(), user.getId())) { // New Cart [add]

                    Cart cart = mapToCart(user, book, dtocart);

                    reponseCartsList.add(mapToCartDto(user, book, cartRepository.save(cart)));

                } else { // Already Exist [update quantity]


                    Cart savedCart = cartRepository.findByBookIdAndUserId(dtocart.getBookId(), user.getId()).get();

                    long newQuantity = dtocart.getQuantity() + savedCart.getQuantity();
                    savedCart.setQuantity(newQuantity);
                    reponseCartsList.add(mapToCartDto(user, book, cartRepository.save(savedCart)));

                }

            }


        } else { // deal with session


            //List<CartDto> sessionCarts = (List<CartDto>) session.getAttribute("sessionCarts");


            Map<Long, CartDto> sessionData =
                    (Map<Long, CartDto>) session.getAttribute("sessionData");


            if (sessionData == null) {
                sessionData = new HashMap<>();

                session.setAttribute("sessionData", sessionData);
            }

            if (sessionData.containsKey(cartDto.getBookId())) {

                long oldQuantity = sessionData.get(cartDto.getBookId()).getQuantity();
                cartDto.setQuantity(
                        oldQuantity + cartDto.getQuantity()
                );
                sessionData.put(cartDto.getBookId(), cartDto);

            } else
                sessionData.put(cartDto.getBookId(), cartDto);


            sessionData.forEach((bookId, cartDto1) -> reponseCartsList.add(cartDto1));
        }


        return reponseCartsList;
    }


    @Override
    public List<CartDto> getAllCartsByUserId(long userId, HttpSession session) {

        System.out.println("HEREEEEEEEEEEEEEEEEEEEEE");

        List<CartDto> cartDtoList = new ArrayList<>();


        if (isLoggedIn()) {

            String email = SecurityContextHolder.getContext().getAuthentication().getName();

            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("User", "id", userId)
            );

            if (userId != user.getId())
                throw new BookStoreException(HttpStatus.BAD_REQUEST, "Users Not Matches ");


            List<Cart> cartList = cartRepository.findAllByUserId(userId);


            cartDtoList = cartList.stream()
                    .map(cart -> mapToCartDto(cart)).collect(Collectors.toList());


        } else {

            Map<Long, CartDto> sessionList = (Map<Long, CartDto>) session.getAttribute("sessionData");

            if (sessionList != null) {

                for (Map.Entry<Long, CartDto> entry : sessionList.entrySet()) {
                    Long bookId = entry.getKey();
                    CartDto cart = entry.getValue();
                    cartDtoList.add(cart);
                }

            }


        }

        return cartDtoList;


    }

    @Override
    public CartDto updateCart(CartDto cartDto, long userId, HttpSession session) {

        if (isLoggedIn()) {

            String email = SecurityContextHolder.getContext().getAuthentication().getName();

            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("User", "id", userId)
            );

            if (userId != user.getId())
                throw new BookStoreException(HttpStatus.BAD_REQUEST, "Users Not Matches ");


            Cart savedCart = cartRepository.findByBookIdAndUserId(cartDto.getBookId(), userId).orElseThrow(
                    () -> new ResourceNotFoundException("Cart", "book-id", cartDto.getBookId())
            );

            savedCart.setQuantity(cartDto.getQuantity());

            return mapToCartDto(cartRepository.save(savedCart));


        } else {


            Map<Long, CartDto> sessionData =
                    (Map<Long, CartDto>) session.getAttribute("sessionData");


            long oldQuantity = sessionData.get(cartDto.getBookId()).getQuantity();
            cartDto.setQuantity(
                    oldQuantity + cartDto.getQuantity()
            );
            sessionData.put(cartDto.getBookId(), cartDto);


            return cartDto;
        }


    }


    @Override
    public String deleteCart(Long bookId, Long userId, HttpSession session) {


        if (isLoggedIn()) {

            String email = SecurityContextHolder.getContext().getAuthentication().getName();

            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("User", "id", userId)
            );


            if (userId != user.getId()) // check equality between (Actual logged user & Request userId in query Param)
                throw new BookStoreException(HttpStatus.BAD_REQUEST, "Bad User Id , Try valid one");


            Cart cart = cartRepository.findByBookIdAndUserId(bookId, userId).get();

            cartRepository.delete(cart);


        } else {

            Map<Long, CartDto> sessionData =
                    (Map<Long, CartDto>) session.getAttribute("sessionData");

            if (!sessionData.containsKey(bookId))
                throw new ResourceNotFoundException("Book", "id", bookId);

            sessionData.remove(bookId);

        }


        return "Cart Deleted Successfully";

    }

    private Cart mapToCart(User user, Book book, CartDto cartDto) {

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setBook(book);
        cart.setQuantity(cartDto.getQuantity());

        return cart;
    }

    private CartDto mapToCartDto(Cart cart) {

        CartDto cartDto = new CartDto();
        cartDto.setBookId(cart.getId());
        cartDto.setUserId(cart.getId());
        cartDto.setQuantity(cart.getQuantity());
        cartDto.setCartId(cart.getId());

        return cartDto;
    }

    private CartDto mapToCartDto(User user, Book book, Cart cart) {

        CartDto cartDto = new CartDto();
        cartDto.setBookId(book.getId());
        cartDto.setUserId(user.getId());
        cartDto.setQuantity(cart.getQuantity());
        cartDto.setCartId(cart.getId());

        return cartDto;
    }


    private boolean isLoggedIn() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (authentication instanceof AnonymousAuthenticationToken) ? false : true;

    }


}
