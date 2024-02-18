package com.springboot.bookstore.repository;

import com.springboot.bookstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(long userId);
    Optional<Order> findByIdAndUserId(long orderId , long userId);

}
