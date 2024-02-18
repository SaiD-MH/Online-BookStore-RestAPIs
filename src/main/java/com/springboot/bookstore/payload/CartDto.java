package com.springboot.bookstore.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private long cartId;
    private Long userId;
    private Long bookId;
    private long quantity;


}
