package com.springboot.bookstore.payload;

import com.springboot.bookstore.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {


    List<Book> bookList;
    private int PageSize;
    private int pageNo;

    private long totalElement;
    private int totalPages;
    private boolean last;


}
