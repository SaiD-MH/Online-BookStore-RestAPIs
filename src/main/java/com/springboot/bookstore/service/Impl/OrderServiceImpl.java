package com.springboot.bookstore.service.Impl;

import com.springboot.bookstore.entity.Book;
import com.springboot.bookstore.entity.Cart;
import com.springboot.bookstore.entity.Order;
import com.springboot.bookstore.entity.User;
import com.springboot.bookstore.exception.BookStoreException;
import com.springboot.bookstore.exception.ResourceNotFoundException;
import com.springboot.bookstore.payload.CheckAllRequest;
import com.springboot.bookstore.payload.OrderResponse;
import com.springboot.bookstore.repository.BookRepository;
import com.springboot.bookstore.repository.CartRepository;
import com.springboot.bookstore.repository.OrderRepository;
import com.springboot.bookstore.repository.UserRepository;
import com.springboot.bookstore.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.springboot.bookstore.utils.OrderStatus.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


    private OrderRepository orderRepository;
    private CartRepository cartRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private BookRepository bookRepository;


    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository,
                            UserRepository userRepository, ModelMapper modelMapper, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderResponse createSingleOrder(Long cartId) {

        Cart cart = cartRepository.findById(cartId).get();

        Order order = fillOrderFields(cart);

        Order savedOrder = orderRepository.save(order);
        cartRepository.delete(cart);

        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);


        return orderResponse;
    }

    @Override
    public List<OrderResponse> checkAllCarts(List<Long> cartsIDs) {


        List<OrderResponse> orderResponseList = cartsIDs.stream().map(
                (cartId) -> createSingleOrder(cartId)
        ).collect(Collectors.toList());

        return orderResponseList;
    }

    @Override
    public List<OrderResponse> getAllOrdersByUserId(long userId) {

        User user = userIdMatchLoggedOne(userId);

        if (user.getId() != userId)
            throw new BookStoreException(HttpStatus.BAD_REQUEST, "Bad User-ID");

        return orderRepository.findByUserId(userId).stream()
                .map((order) -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());


    }

    @Override
    public OrderResponse getOrderByOrderIdAndUserId(long orderId, long userId) {


        User user = userIdMatchLoggedOne(userId);


        if (user.getId() != userId)
            throw new BookStoreException(HttpStatus.BAD_REQUEST, "Bad User-ID");

        Order order = orderRepository.findByIdAndUserId(orderId, userId).orElseThrow(
                () -> new BookStoreException(HttpStatus.BAD_REQUEST, "Order id or User id are in valid")
        );
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public String cancelOrder(long orderId) {


        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Orders", "id", orderId));
        Book book = bookRepository.findById(order.getBookId()).orElseThrow(
                () -> new ResourceNotFoundException("Books", "id", order.getBookId())
        );

        User user = userIdMatchLoggedOne(order.getUser().getId());


        Cart cart = new Cart();
        cart.setUser(user);
        cart.setBook(book);
        cart.setQuantity(order.getQuantity());

        cartRepository.save(cart);

        return "Order Cancel Successfully Check your Cart list again";
    }

    private Order fillOrderFields(Cart cart) {

        Order order = new Order(0,
                cart.getBook().getId(),
                cart.getQuantity(),
                cart.getUser(),
                new Date(),
                cart.getQuantity() * cart.getBook().getPrice(),
                COMPLETED,
                cart.getBook().getTitle()

        );

        return order;

    }

    private User userIdMatchLoggedOne(long userId) {

        User user = userRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId)
        );

        if (user.getId() != userId)
            throw new BookStoreException(HttpStatus.BAD_REQUEST, "Bad User-ID");


        return user;

    }


}
