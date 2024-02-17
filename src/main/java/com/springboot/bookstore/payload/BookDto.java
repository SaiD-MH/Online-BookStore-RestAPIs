package com.springboot.bookstore.payload;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.Data;

import java.util.List;

@Data
public class BookDto {
    private int id ;
    private String title ;
    private List<String> authors;
    private double price;
    private String category;
    private int quantity;


}
