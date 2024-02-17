package com.springboot.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;
    @ElementCollection(fetch = FetchType.EAGER )
    private List<String> authors;
    @Column(name = "price")
    private double price;

    @Column(name = "category")
    private String category;
    @Column(name = "quantity")
    private int quantity;


}
