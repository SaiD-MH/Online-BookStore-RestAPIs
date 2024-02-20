package com.springboot.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.REMOVE)
    private Book book;
    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.REMOVE )
    private User user;
    @Column(name = "quantity")
    private long quantity;

}
