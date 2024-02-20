package com.springboot.bookstore.service;

import com.springboot.bookstore.payload.CheckAllRequest;
import com.springboot.bookstore.payload.OrderResponse;

import java.util.List;

public interface OrderService {


    OrderResponse createSingleOrder(Long cartId);
    List<OrderResponse>checkAllCarts(List<Long> cartsIDs);

    List<OrderResponse>getAllOrdersByUserId(long userId);

    OrderResponse getOrderByOrderIdAndUserId(long orderId , long userId);


    String cancelOrder(long orderId);

}
