package com.springboot.bookstore.controller;

import com.springboot.bookstore.payload.CheckAllRequest;
import com.springboot.bookstore.payload.OrderResponse;
import com.springboot.bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "REST APIs for Order Resource")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<OrderResponse> createSingleOrder(@RequestParam("cartId") Long cartId) {


        return new ResponseEntity<>(orderService.createSingleOrder(cartId), HttpStatus.CREATED);

    }

    @PostMapping("/checkAll")
    public ResponseEntity<List<OrderResponse>> checkAll(@RequestBody CheckAllRequest cartIDs) {

        return ResponseEntity.ok(orderService.checkAllCarts(cartIDs.getCartIDs()));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(@RequestParam("userId") long userId) {

        return ResponseEntity.ok(orderService.getAllOrdersByUserId(userId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderByOrderIdAndUserId(@PathVariable("orderId") long orderId,
                                                                    @RequestParam("userId") long userId) {

        return ResponseEntity.ok(orderService.getOrderByOrderIdAndUserId(orderId, userId));
    }


    @DeleteMapping("cancelOrder/{orderId}")
    public String cancelOrder(@PathVariable("orderId") long orderId) {

        return orderService.cancelOrder(orderId);
    }


}
