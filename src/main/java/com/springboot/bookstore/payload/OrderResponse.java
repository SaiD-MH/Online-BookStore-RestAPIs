package com.springboot.bookstore.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private long orderId;
    private String  bookTitle;
    private long quantity;
    private Date date;
    private String state;
    private double price;


}
