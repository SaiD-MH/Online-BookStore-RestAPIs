package com.springboot.bookstore.service;

import com.springboot.bookstore.payload.BookDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BookService {

    BookDto createBook(BookDto bookDto);
    List<BookDto> getAllBooks();

    BookDto getBookId(long id);

    BookDto updateBook(BookDto bookDto , long id);


    String deleteBook(long id);


}
