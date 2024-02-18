package com.springboot.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @Column(name = "book_id")
    private long bookId;

    @Column(name = "quantity")
    private long quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    User user;

    @Column(name = "date")
    private Date date;


    @Column(name = "totalPrice")
    private double totalPrice;
    @Column(name = "state")
    private String state;

    @Column(name = "bookTitle")
    private  String bookTitle;

}
